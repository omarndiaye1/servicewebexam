package com.mycompany.myapp.web.endpoints;

import com.mycompany.myapp.domain.Personne;
import com.mycompany.myapp.domain.Sencovid;
import com.mycompany.myapp.repository.SencovidRepository;
import com.mycompany.myapp.wsdl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

@Endpoint
public class SencovidSoapEndpoint {

    @Autowired
    SencovidRepository sencovidRepository;
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:8094/api/personnes";
    String accessToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJxLTFRQWpHR3R5TC1fRzhWT1lCTDgxWWxFRjgzb0xMTG5ZR0lLVl9ZSWE0In0.eyJleHAiOjE2MTYyMzM3NzYsImlhdCI6MTYxNjE5Nzc3NiwianRpIjoiNTVhNDAyODctMGRhZC00ZGVjLWFhZTktYWJmZmE1NGU1NDVjIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo5MDgwL2F1dGgvcmVhbG1zL2poaXBzdGVyIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjRjOTczODk2LTU3NjEtNDFmYy04MjE3LTA3YzVkMTNhMDA0YiIsInR5cCI6IkJlYXJlciIsImF6cCI6ImpoaXBzdGVyLXJlZ2lzdHJ5Iiwic2Vzc2lvbl9zdGF0ZSI6ImFkMjhlZDg5LWIyODYtNGYyNS04MzM4LTM0ZjA0MzM4Y2YxMCIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovLzEyNy4wLjAuMTo4NzYxIiwiaHR0cDovL2xvY2FsaG9zdDo4NzYxIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJST0xFX1VTRVIiLCJvZmZsaW5lX2FjY2VzcyIsIlJPTEVfQURNSU4iLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGpoaXBzdGVyIGVtYWlsIHByb2ZpbGUiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwicm9sZXMiOlsiUk9MRV9VU0VSIiwib2ZmbGluZV9hY2Nlc3MiLCJST0xFX0FETUlOIiwidW1hX2F1dGhvcml6YXRpb24iXSwibmFtZSI6IkFkbWluIEFkbWluaXN0cmF0b3IiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhZG1pbiIsImdpdmVuX25hbWUiOiJBZG1pbiIsImZhbWlseV9uYW1lIjoiQWRtaW5pc3RyYXRvciIsImVtYWlsIjoiYWRtaW5AbG9jYWxob3N0In0.ZnzWVSGsRRNL39vNo-Wegv8DY13b5yX7lz79lMlNlJ1ExSIZZVAgfk8KtrYiA52BdEK0JSCl-V-4ZnVPUyqX45OSAVZA4DWojQM2slvz7oPNFjxt0U0Yhw5_OJJ09BuKDYOLjq3IQM6E1VUK8LUKbCXCE-GDOltHsmfihdP8t0LY_E6zTOQJZpsAAu3plRH_rcIZErntm6Skpl4v5x_BsUyGAfY9fkRNUZE2ErXj8Ve6X-tcyjDJVEloFjJAbtqaQW0Gj5FLxzo6JAjqMAMp4lqaHvfirIB0Ojcn61-kx4XKPdWCMZvIgoxez3lnU5IE-IhiIDY02-Weqm7eYhakrQ" ;
    DatatypeFactory datatypeFactory;

    {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
    }




    @PayloadRoot(namespace = "http://ws.groupeisi.com",localPart = "getInfoRequest")
    public @ResponsePayload
    GetInfoResponse getInfoRequest (@RequestPayload GetInfoRequest request) {
        GetInfoResponse response= new GetInfoResponse();
        response.setOutput("Bonjour M2GL "+ request.getInput());
        return response;
    }

    @PayloadRoot(namespace = "http://ws.groupeisi.com",localPart = "getCovid19InfoRequest")
    public @ResponsePayload GetCovid19InfoResponses getCovid19InfoRequest (@RequestPayload GetCovid19InfoRequest request) throws DatatypeConfigurationException, JAXBException {
        GetCovid19InfoResponse covidresponse2= new GetCovid19InfoResponse();

        List<GetCovid19InfoResponse> covidresponse= new ArrayList<GetCovid19InfoResponse>();
        GetCovid19InfoResponses getCovid19InfoResponses = new GetCovid19InfoResponses();
        getCovid19InfoResponses.setGetCovid19InfoResponseList(new ArrayList<GetCovid19InfoResponse>());

        long bb = Long.parseLong(request.getInput());
        System.out.println("Input");
        System.out.println(bb);
        //Sencovid sencovid = sencovidRepository.getCovidInfoById((long)11);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+accessToken);
        HttpEntity<Sencovid[]> entity = new HttpEntity<Sencovid[]>(headers);

        ResponseEntity<Personne[]> response = restTemplate.exchange(url, //
            HttpMethod.GET, entity, Personne[].class);

        HttpStatus statusCode = response.getStatusCode();
        System.out.println("Response Satus Code: " + statusCode);

        // Status Code: 200
        if (statusCode == HttpStatus.OK) {
            // Response Body Data
            Personne[] list = response.getBody();

            if (list != null) {
                for (Personne e : list) {
                    System.out.println("Covid: " + e.getNbrtest() + " - " + e.getSuivis());
                    //getCovid19InfoResponses.getCovid19InfoResponseList().add(e);
                    covidresponse2.setNbrtest(e.getNbrtest());
                    covidresponse2.setCascommunautaire(e.getCascommunautaire());
                    covidresponse2.setPostifcase(e.getPositif());
                    covidresponse2.setDeces(e.getDeces());
                    covidresponse2.setSuivis(e.getSuivis());
                    getCovid19InfoResponses.getCovid19InfoResponseList().add(covidresponse2);
                    covidresponse2 = new GetCovid19InfoResponse();

                }


            }


        }

        /*JAXBContext jaxbContext = JAXBContext.newInstance(GetCovid19InfoResponses.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(getCovid19InfoResponses, System.out);*/
        //jaxbMarshaller.

        return getCovid19InfoResponses;
    }

    /*public Date StringToDate(String s){

        Date result = null;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result  = dateFormat.parse(s);
        }

        catch(ParseException e){
            e.printStackTrace();

        }
        return result ;
    }*/
}
