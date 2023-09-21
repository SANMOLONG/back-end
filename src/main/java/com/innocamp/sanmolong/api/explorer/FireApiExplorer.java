package com.innocamp.sanmolong.api.explorer;

import com.innocamp.sanmolong.api.dto.FireDto;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FireApiExplorer {
    @Value("${api.key}")
    private String serviceKey;
    private String apiUrl = "http://apis.data.go.kr/1400377/forestPoint/forestPointListSigunguSearch"; // 산불위험예보(시군구, 시도)

    public List<FireDto> getFireInfo() throws IOException {
        // 강원도 시도 코드 51
        // 치악산 51130
        // 태백산 51190
        // 오대산 51760
        // 설악산 51210
        Map<String, String> map = new HashMap<>();
        map.put("치악산", "51130");
        map.put("태백산", "51190");
        map.put("오대산", "51760");
        map.put("설악산", "51210");

        List<FireDto> fireDtoList = new ArrayList<>();
        for( Map.Entry<String, String> entry : map.entrySet() ){
            String mount = entry.getKey();
            String code = entry.getValue();

            String result = getInfoFromApi(code);
            int risk = parse(result);
            FireDto fireDto = new FireDto(mount, risk);
            fireDtoList.add(fireDto);
        }

        return fireDtoList;
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
        jsonObj_4 = jsonObj_4.getJSONObject("item");


        // meanavg: 평균 산불위험지수
        return jsonObj_4.getInt("meanavg");
    }

    private String getInfoFromApi(String code) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(apiUrl); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*응답 결과의 출력 방식을 xml, json형태로 변환 제공될 수 있도록 함*/
        urlBuilder.append("&" + URLEncoder.encode("localAreas","UTF-8") + "=" + URLEncoder.encode(code, "UTF-8")); /*시군구 코드 5자리*/
        urlBuilder.append("&" + URLEncoder.encode("upplocalcd","UTF-8") + "=" + URLEncoder.encode("51", "UTF-8")); /*시도 코드 2자리*/
        urlBuilder.append("&" + URLEncoder.encode("excludeForecast","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*출력결과에서 예보정보를 제외할지 여부 1 : 제외 0 : 포함*/

        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

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