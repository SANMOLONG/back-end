package com.innocamp.sanmolong.api.explorer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.*;

@Service
public class SunApiExplorer {
    @Value("${api.key}")
    private String serviceKey;
    private String apiUrl = "http://apis.data.go.kr/B090041/openapi/service/RiseSetInfoService/getLCRiseSetInfo"; // 일출일몰
    public List<String> getSunInfo(String mount) throws IOException, ParserConfigurationException, SAXException {
        // 치악산 37.36468577871721, 128.05763205580345 / 3721, 12803
        // 태백산 37.09261387531276, 128.93470805184012 / 3705, 12856
        // 오대산 37.76623987207154, 128.59952367373563 / 3745, 12835
        // 설악산 38.12010147343754, 128.4548708590278 / 3807, 12827
        Map<String, List<String>> map = new HashMap<>();
        map.put("치악산", Arrays.asList("3721", "12803"));
        map.put("태백산", Arrays.asList("3705", "12856"));
        map.put("오대산", Arrays.asList("3745", "12835"));
        map.put("설악산", Arrays.asList("3807", "12827"));

        String result = getInfoFromApi(map.get(mount));

        return parse(result);
    }

    private List<String> parse(String result) throws ParserConfigurationException, IOException, SAXException {
        // xml에서 데이터 파싱
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new InputSource(new StringReader(result)));
        doc.getDocumentElement().normalize();

        NodeList nodelist = doc.getElementsByTagName("sunrise");
        Node textNode = nodelist.item(0).getChildNodes().item(0);
        StringBuilder sb = new StringBuilder(textNode.getNodeValue().trim());
        sb.insert(2, ":");
        String sunrise = sb.toString();

        nodelist = doc.getElementsByTagName("sunset");
        textNode = nodelist.item(0).getChildNodes().item(0);
        sb = new StringBuilder(textNode.getNodeValue().trim());
        sb.insert(2, ":");
        String sunset = sb.toString();

        return Arrays.asList(sunrise, sunset);
    }

    private String getInfoFromApi(List<String> location) throws IOException {
        StringBuilder sb = new StringBuilder(apiUrl); /*URL*/
        sb.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
        LocalDate now = LocalDate.now();
        String today = now.toString().replace("-", "");
        sb.append("&" + URLEncoder.encode("locdate","UTF-8") + "=" + URLEncoder.encode(today, "UTF-8")); /*날짜(연월일)*/
        sb.append("&" + URLEncoder.encode("latitude","UTF-8") + "=" + URLEncoder.encode(location.get(0), "UTF-8")); /*위도(도, 분형태)*/
        sb.append("&" + URLEncoder.encode("longitude","UTF-8") + "=" + URLEncoder.encode(location.get(1), "UTF-8")); /*경도(도, 분형태)*/
        sb.append("&" + URLEncoder.encode("dnYn","UTF-8") + "=" + URLEncoder.encode("N", "UTF-8")); /*실수형태(129.xxx)일경우 Y, 도와 분(128도 00분)형태의 경우 N*/

        URL url = new URL(sb.toString());

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

        sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();
        conn.disconnect();
        return sb.toString();
    }
}