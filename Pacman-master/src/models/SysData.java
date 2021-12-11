package models;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import models.Answer;
import models.Question;

public class SysData {

	private static SysData instance;

	public static SysData getInstance() {
		if (instance == null)
			instance = new SysData();
		return instance;
	}
	
	
	/*
	 * This method reads the questions written in JSON file and returns them in an
	 * array list
	 */
	@SuppressWarnings("deprecation")
	public static ArrayList<Question> readQuestionsJSON() {
		ArrayList<Question> arrlistq = new ArrayList<Question>();
		try {
			Object obj = new JSONParser().parse(new FileReader("questionsJSON.json"));
			JSONObject jo = (JSONObject) obj;
			JSONArray arr = (JSONArray) jo.get("questions");
	
			for (Object questionObj : arr) {
				JSONObject jsonQObjt = (JSONObject) questionObj;
				int questionID = Math.toIntExact((Long) jsonQObjt.get("ID"));
				String context = (String) jsonQObjt.get("question");
				JSONArray answersarr = (JSONArray) jsonQObjt.get("answers");
				ArrayList<Answer> arrlista = new ArrayList<Answer>();
				@SuppressWarnings("rawtypes")
				Iterator<?> itr = answersarr.iterator();
				// answerID
				int i = 1;
				while (itr.hasNext()) {
					String content = itr.next().toString();
					Answer an = new Answer(i, questionID, content);
					i++;
					arrlista.add(an);
				}
				int correct_ans = Math.toIntExact((Long) jsonQObjt.get("correct_ans"));
				String difficulty = (String) jsonQObjt.get("level");
	
				Question q = new Question(questionID, context, difficulty, arrlista, correct_ans);
				arrlistq.add(q);
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrlistq;
	}

	/*
	 * Given an array list this method overrides the JSON questions file with the
	 * questions in the array list
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static boolean addQuestionToJSON(Question q) throws FileNotFoundException {
		JSONParser jsonParser = new JSONParser();

        try {
            JSONObject obj = (JSONObject) jsonParser.parse(new FileReader("questionsJSON.json"));
            
            JSONArray jsonArray = (JSONArray) obj.get("questions");
                      
            JSONObject question = new JSONObject();
            question.put("ID", q.getQuestionID());
            question.put("question", q.getContent());
            JSONArray ans = new JSONArray();
            for(Answer a : q.getAnswers()) {
            	ans.add(a.getContent());
            }
            question.put("answers", ans);
            question.put("correct_ans", q.getCorrect_ans());
            question.put("level", q.getDifficulty());

            jsonArray.add(question);
            
            FileWriter file = new FileWriter("questionsJSON.json");
            file.write(obj.toJSONString());
            file.flush();
            file.close();
            
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return true;
	}
	
    public static void deleteQuestionFromJSON(Question q) {
      	 try {
      	        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader("questionsJSON.json"));
      	        JSONArray jsonArray = (JSONArray) jsonObject.get("questions");
      	        
      	        JSONArray aux = (JSONArray) jsonArray.clone();
      	        
      	        for(Object o : aux) {
      	        	JSONObject jo = (JSONObject) o;
      	        	if(Math.toIntExact((Long) jo.get("ID")) == q.getQuestionID()) {
   	                System.out.println("REMOVING QUESTION:");
   	                System.out.println(jo.get("question"));
   	                jsonArray.remove(o);
      	        	}
      	        }
      	        try (FileWriter file = new FileWriter("questionsJSON.json")) { //store data
      	            file.write(jsonObject.toJSONString());
      	            file.flush();
      	        }
      	    } catch (IOException | ParseException ex) {
      	        System.out.println("Error: " + ex);
      	    }
      }
 
    public static void deleteQuestionFromJSONByID(int questionID) {

    	 try {
    	        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader("questionsJSON.json"));
    	        JSONArray jsonArray = (JSONArray) jsonObject.get("questions");
    	        
    	        JSONArray aux = (JSONArray) jsonArray.clone();
    	        
    	        for(Object o : aux) {
    	        	JSONObject jo = (JSONObject) o;
    	        	if(Math.toIntExact((Long) jo.get("ID")) == questionID) {
 	                System.out.println("REMOVING QUESTION:");
 	                System.out.println(jo.get("question"));
 	                jsonArray.remove(o);
    	        	}
    	        }
    	        try (FileWriter file = new FileWriter("questionsJSON.json")) { //store data
    	            file.write(jsonObject.toJSONString());
    	            file.flush();
    	        }
    	    } catch (IOException | ParseException ex) {
    	        System.out.println("Error: " + ex);
    	    }
    
   }
    
    public static void editQuestionInJSON(Question newQuestion) {

     	 try {
     	        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader("questionsJSON.json"));
     	        JSONArray jsonArray = (JSONArray) jsonObject.get("questions");
     	        
     	        JSONArray aux = (JSONArray) jsonArray.clone();
     	        
     	        for(Object o : aux) {
     	        	JSONObject jo = (JSONObject) o;
     	        	if(Math.toIntExact((Long) jo.get("ID")) == newQuestion.getQuestionID()) {
	  	                jo.put("question", newQuestion.getContent());
	  	                jo.put("level", newQuestion.getDifficulty());
		  	            JSONArray ans = new JSONArray();
		  	            for(Answer a : newQuestion.getAnswers()) {
		  	            	ans.add(a.getContent());
		  	            }
	  	              	jo.put("answers", ans);
	  	            	jo.put("correct_ans", newQuestion.getCorrect_ans());
     	        	}
     	        }
     	        try (FileWriter file = new FileWriter("questionsJSON.json")) { //store data
     	            file.write(jsonObject.toJSONString());
     	            file.flush();
     	        }
     	    } catch (IOException | ParseException ex) {
     	        System.out.println("Error: " + ex);
     	    }
     
    }

}
