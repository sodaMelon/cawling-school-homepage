package test;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

class CookieTest {

    @Test
    public void test() {
        RestTemplate template = new RestTemplate();
        UriComponents uri = UriComponentsBuilder.fromHttpUrl("https://krkyodong.gwe.es.kr/boardCnts/view.do?s=krkyodong&m=0203&boardID=36401&boardSeq=8389776&lev=0&page=1").build();
        HttpHeaders requestHeader = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(requestHeader);
        //uri1로 1차 접속
        HttpEntity<String> response = template.exchange(uri.toUri(), HttpMethod.GET, entity, String.class);
        HttpHeaders responseHeader = response.getHeaders();

        //1차 접속 request에서 cookie추출해서 2차 접속 준비
        List<String> cookie = responseHeader.get("Set-Cookie");
        requestHeader.add("Cookie", cookie.get(0));
        requestHeader.add("Cookie", cookie.get(1));
        //		 headers.setContentType(MediaType.MULTIPART_FORM_DATA);<- 이걸로 교체하면 됨 !! (☜)
        // requestHeader.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        // requestHeader.add(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.name());
        // 2차 접속 uri 준비
        uri = UriComponentsBuilder.fromHttpUrl("https://krkyodong.gwe.es.kr/boardCnts/fileDown.do?m=0203&s=krkyodong&fileSeq=1311cefd405c0d0b3e1631623172769c").build();

        // uri로 2차 접속
        //HttpEntity<Object> response2 = template.exchange(uri.toUri(), HttpMethod.GET, entity, Object.class);
        /**
         * 00:01:06.344 [Test worker] DEBUG org.springframework.web.client.RestTemplate - HTTP GET https://krkyodong.gwe.es.kr/boardCnts/fileDown.do?m=0203&s=krkyodong&fileSeq=1311cefd405c0d0b3e1631623172769c
         * 00:01:06.376 [Test worker] DEBUG org.springframework.web.client.RestTemplate - Accept=[application/json, application/*+json]
         * 00:01:06.399 [Test worker] DEBUG org.springframework.web.client.RestTemplate - Response 200 OK
         *
         * Invalid mime type "application-download;charset=UTF-8": does not contain '/'
         *
         */
        //todo 200으로 cdn 주소는 접근 가능하나 ↑ 위 내용에 대한 적절한 해결 필요



    }
}
