package com.innocamp.sanmolong.api.explorer;

import com.innocamp.sanmolong.api.dto.WeatherDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WeatherApiExplorer {
    @Value("${api.key}")
    private String serviceKey;
    private String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"; // 단기예보

    private final SunApiExplorer sunApiExplorer;
    public List<WeatherDto> getWeatherInfo() throws IOException, ParserConfigurationException, SAXException {
        // 치악산 37.36468577871721, 128.05763205580345 / 78, 123
        // 태백산 37.09261387531276, 128.93470805184012 / 94, 117
        // 오대산 37.76623987207154, 128.59952367373563 / 88, 132
        // 설악산 38.12010147343754, 128.4548708590278 / 85, 139
        Map<String, List<String>> map = new HashMap<>();
        map.put("치악산", Arrays.asList("78", "123"));
        map.put("태백산", Arrays.asList("94", "117"));
        map.put("오대산", Arrays.asList("88", "132"));
        map.put("설악산", Arrays.asList("85", "139"));

        List<WeatherDto> weatherDtoList = new ArrayList<>();

        for( Map.Entry<String, List<String>> entry : map.entrySet() ){
            String result = getInfoFromApi(entry.getValue());
            int forecast = parse(result);
            List<String> sun = sunApiExplorer.getSunInfo(entry.getKey());
            boolean hiking = true;
            WeatherDto weatherDto = new WeatherDto(entry.getKey(), hiking, sun.get(0), sun.get(1), forecast);
            weatherDtoList.add(weatherDto);
        }
        return weatherDtoList;
    }

    private int parse(String result) {
        // json에서 데이터 파싱

        // response 키를 가지고 데이터를 파싱
        JSONObject jsonObj_1 = new JSONObject(result);
        JSONObject jsonObj_2 = jsonObj_1.getJSONObject("response");

        // response 로 부터 body 찾기
        JSONObject jsonObj_3 = jsonObj_2.getJSONObject("body");

        // body로 부터 items 찾기
        JSONObject jsonObj_4 = jsonObj_3.getJSONObject("items");

        // items로 부터 itemlist 를 받기
        JSONArray jsonArray = jsonObj_4.getJSONArray("item");

        int forecast = 0;
        for(int i=0; i < jsonArray.length(); i++) {
            jsonObj_4 = jsonArray.getJSONObject(i);
            String fcstValue = jsonObj_4.getString("fcstValue");
            String category = jsonObj_4.getString("category");

            if (category.equals("SKY")) {
                forecast = Integer.parseInt(fcstValue);
                break;
            }
        }
        return forecast;
    }

    private String getInfoFromApi(List<String> location) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(apiUrl); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("12", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
//        LocalDate now = LocalDate.now();
//        String today = now.toString().replace("-", "");
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("20230921", "UTF-8")); /*‘21년 6월 28일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("0800", "UTF-8")); /*05시 발표(3시마다) */
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(location.get(0), "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(location.get(1), "UTF-8")); /*예보지점의 Y 좌표값*/

        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            System.out.println("Response code: " + conn.getResponseCode());
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            throw new RuntimeException("에러 발생");
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();
        conn.disconnect();

        return sb.toString();
    }
}