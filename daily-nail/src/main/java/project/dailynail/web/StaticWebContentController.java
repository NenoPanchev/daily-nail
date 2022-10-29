package project.dailynail.web;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
public class StaticWebContentController {

    private Map<String, byte[]> cache = new HashMap<String,byte[]>();

    @RequestMapping(value = "/css/{file}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getCssFile(@PathVariable("file") String name){
        ResponseEntity<byte[]> responseEntity = loadResource(".\\static\\css\\"+name,"text/css");
        return responseEntity;
    }

    @RequestMapping(value = "/css/images/{file}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getCssImageFile(@PathVariable("file") String name){
        ResponseEntity<byte[]> responseEntity = loadResource(".\\static\\css\\images\\"+name,"image/svg+xml");
        return responseEntity;
    }

    @RequestMapping(value = "/img/bootstrap-icons-1.1.0/{file}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getimgFile(@PathVariable("file") String name){
        ResponseEntity<byte[]> responseEntity = loadResource(".\\static\\img\\bootstrap-icons-1.1.0\\"+name,"image/svg+xml");
        return responseEntity;
    }

    @RequestMapping(value = "/js/{file}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getJsFile(@PathVariable("file") String name){
        ResponseEntity<byte[]> responseEntity = loadResource(".\\static\\js\\"+name,"text/javascript");
        return responseEntity;
    }

    @RequestMapping(value = "/fonts/{file}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getFontFile(@PathVariable("file") String name){
        ResponseEntity<byte[]> responseEntity = null;

        try {
            responseEntity = loadResource(".\\static\\fonts\\"+name,"image/svg+xml");
            return responseEntity;
        } catch (ResponseStatusException ignored) {

        }

        try {
            responseEntity = loadResource(".\\static\\fonts\\"+name,"application/vnd.ms-fontobject");
            return responseEntity;
        } catch (ResponseStatusException ignored) {

        }

        try {
            responseEntity = loadResource(".\\static\\fonts\\"+name,"application/font-woff");
            return responseEntity;
        } catch (ResponseStatusException ignored) {

        }

        try {
            responseEntity = loadResource(".\\static\\fonts\\"+name,"application/octet-stream");
            return responseEntity;
        } catch (ResponseStatusException ignored) {

        }
        return responseEntity;
    }

    @RequestMapping(value = "/images/{file}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImageFile(@PathVariable("file") String name){
        ResponseEntity<byte[]> responseEntity = loadResource(".\\static\\images\\"+name,"image/svg+xml");
        return responseEntity;
    }

    @RequestMapping(value = "/images/weather-icons/{file}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getWeatherImageFile(@PathVariable("file") String name){
        ResponseEntity<byte[]> responseEntity = loadResource(".\\static\\images\\weather-icons\\"+name,"image/svg+xml");
        return responseEntity;
    }

    @RequestMapping(value = "/img/{file}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImgFile(@PathVariable("file") String name){
        ResponseEntity<byte[]> responseEntity = loadResource(".\\static\\img\\"+name,"image/svg+xml");
        return responseEntity;
    }

//    @RequestMapping(value = "/jsons/{file}", method = RequestMethod.GET)
//    public ResponseEntity<byte[]> getJsonFile(@PathVariable("file") String name){
//        ResponseEntity<byte[]> responseEntity = loadResource(".\\static\\jsons\\"+name,"application/json");
//        return responseEntity;
//    }

    private ResponseEntity<byte[]> loadResource(String path, String contentType){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", contentType);
        if(hasCachedContent(path)){
            return new ResponseEntity<byte[]>(getCachedContent(path),responseHeaders, HttpStatus.OK);
        }else{
            Resource resource = new ClassPathResource(path);
            if(resource.exists()){
                try{
                    InputStream inputStream = resource.getInputStream();
                    byte[] content = inputStream.readAllBytes();
                    putCache(path, content);
                    return new ResponseEntity<byte[]>(content,responseHeaders,HttpStatus.OK);
                }catch(IOException e){
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
                }
            }else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The requested resource '"+path+"' does not exist'");
            }
        }
    }

    private byte[] getCachedContent(String path){
        return cache.get(path);
    }

    private boolean hasCachedContent(String path){
        return cache.containsKey(path);
    }

    private void putCache(String path, byte[] content){
        cache.put(path, content);
    }

}