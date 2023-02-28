package br.com.the475group.diagnosticar.sincronizacao;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class Sincronizar {
    
    private static final String URL = "";
    
    public static String toJson(Object obj){
        //TODO Definir Tipo do Objeto "obj"
        Gson gson = new Gson();  
        String json = gson.toJson(obj);  
        return json;  
    }
    
    public static String sincronizar(Object obj) {
        return sincronizar( toJson(obj) );
    }
    
    public static String sincronizar(String json) {  
        String jsonResp = "";
    	
    	try {  
            DefaultHttpClient httpClient = new DefaultHttpClient();  
            HttpPost post = new HttpPost(URL);  
 
            post.setEntity(new StringEntity(json));  
            post.setHeader("Accept", "Application/json");  
            post.setHeader("Content-type", "application/json");  
 
            HttpResponse response = httpClient.execute(post);
            jsonResp = EntityUtils.toString( response.getEntity() );
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  

        return jsonResp;
    }
    
}