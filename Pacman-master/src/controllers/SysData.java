package controllers;

import models.*;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javax.swing.Timer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class SysData {

	private static SysData instance;
	public static ArrayList<Question> allQuestions = readQuestionsJSON();
	private static ArrayList<RecordWinner> oldTopTenWinnersAL = new ArrayList<RecordWinner>();
	public static ArrayList<Timer> allTimers = new ArrayList<Timer>();

	private static String thisUser;
	private static String pacMode;
	private static String gameMode;
	
	//Used to get correct file path that the JAR file can read.
	public static final File temp = new File("");
	public static final char[] abPath = temp.getAbsolutePath().toCharArray();
	public static final String correctedPath = String.valueOf(abPath).replace("target", "");
	
	
	
	public static SysData getInstance() {
		if (instance == null)
			instance = new SysData();
		return instance;
	}
	
	
	/*
	 * This method reads the questions written in JSON file and returns them in an
	 * array list
	 */
	public static ArrayList<Question> readQuestionsJSON() {
		ArrayList<Question> arrlistq = new ArrayList<Question>();
		try {
			Object obj = new JSONParser().parse(new FileReader(correctedPath + "/questionsJSON.json"));
			JSONObject jo = (JSONObject) obj;
			JSONArray arr = (JSONArray) jo.get("questions");
	
			for (Object questionObj : arr) {
				JSONObject jsonQObjt = (JSONObject) questionObj;
				int questionID = Math.toIntExact((Long) jsonQObjt.get("ID"));
				String context = (String) jsonQObjt.get("question");
				JSONArray answersarr = (JSONArray) jsonQObjt.get("answers");
				ArrayList<Answer> arrlista = new ArrayList<Answer>();
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
			System.err.println("");
			return arrlistq;
		}
		return arrlistq;
	}

	/*
	 * Given an array list this method overrides the JSON questions file with the
	 * questions in the array list
	 */
	@SuppressWarnings({ "unchecked" })
	public static boolean addQuestionToJSON(Question q) throws FileNotFoundException {
		
		JSONParser jsonParser = new JSONParser();

        try {
            JSONObject obj = (JSONObject) jsonParser.parse(new FileReader(correctedPath + "/questionsJSON.json"));
            
            JSONArray jsonArray = (JSONArray) obj.get("questions");
            int size = 0;
            if(!readQuestionsJSON().isEmpty())
            	size = readQuestionsJSON().get(readQuestionsJSON().size()-1).getQuestionID();
            JSONObject question = new JSONObject();
            question.put("ID", size+1);
            question.put("question", q.getContent());
            JSONArray ans = new JSONArray();
            for(Answer a : q.getAnswers()) {
            	ans.add(a.getContent());
            }
            question.put("answers", ans);
            question.put("correct_ans", q.getCorrect_ans());
            question.put("level", q.getDifficulty());

            jsonArray.add(question);
            
            FileWriter file = new FileWriter(correctedPath + "/questionsJSON.json");
            file.write(obj.toJSONString());
            file.flush();
            file.close();
            
        } catch (ParseException | IOException e) {
        	System.out.println("Couldn't add question, check if the JSON file is formatted correctly!");
        	return false;
        }
        return true;
	}
	
    public static boolean deleteQuestionFromJSON(Question q) {
    	
      	 try {
      	        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader(correctedPath + "/questionsJSON.json"));
      	        JSONArray jsonArray = (JSONArray) jsonObject.get("questions");
      	        
      	        JSONArray aux = (JSONArray) jsonArray.clone();
      	        
      	        for(Object o : aux) {
      	        	JSONObject jo = (JSONObject) o;
      	        	if(Math.toIntExact((Long) jo.get("ID")) == q.getQuestionID()) {
	   	                System.out.println("REMOVING QUESTION:");
	   	                System.out.println(jo.get("question"));
	   	                allQuestions = readQuestionsJSON();
	   	                jsonArray.remove(o);
	   	                return true;
      	        	}
      	        	else
      	        		return false;
      	        }
      	        try (FileWriter file = new FileWriter(correctedPath + "/questionsJSON.json")) { //store data
      	            file.write(jsonObject.toJSONString());
      	            file.flush();
      	        }
      	    } catch (IOException | ParseException ex) {
      	        System.out.println("Error: " + ex);
      	    }
      	 if(readQuestionsJSON().contains(q))
      		 return false;
      	 else
      		 return true;
      }
 
    public static boolean deleteQuestionFromJSONByID(int questionID) {

    	 try {
    	        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader(correctedPath + "/questionsJSON.json"));
    	        JSONArray jsonArray = (JSONArray) jsonObject.get("questions");
    	        
    	        JSONArray aux = (JSONArray) jsonArray.clone();
    	        
    	        for(Object o : aux) {
    	        	JSONObject jo = (JSONObject) o;
    	        	if(Math.toIntExact((Long) jo.get("ID")) == questionID) {
	 	                jsonArray.remove(o);
    	        	}
    	        }
    	        
    	        try (FileWriter file = new FileWriter(correctedPath + "/questionsJSON.json")) { //store data
    	            file.write(jsonObject.toJSONString());
    	            file.flush();
    	        }
    	    } catch (IOException | ParseException ex) {
    	        System.out.println("Error: " + ex);
    	    }
    	 boolean flag = true;
    	 for(Question q: readQuestionsJSON()) {
    	    if(q.getQuestionID() == questionID)
         		 flag = false;
    	 }
    	 if(flag == false)
    		 return false; 
    	 else 
    		 return true;
   }
    
    public static boolean editQuestionInJSON(Question newQuestion) {

     	 try {
     	        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader(correctedPath + "/questionsJSON.json"));
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
     	        try (FileWriter file = new FileWriter(correctedPath + "/questionsJSON.json")) { //store data
     	            file.write(jsonObject.toJSONString());
     	            file.flush();
     	        }
     	    } catch (IOException | ParseException ex) {
     	        System.out.println("Error: " + ex);
     	    }
     	 boolean flag = false;
     	 for(Question q: readQuestionsJSON()) {
     		 if(q.getQuestionID() == newQuestion.getQuestionID()) {
     			flag = true;
 		 		break;
     		 }
     	 }
     	 if(flag == false)
     		 return false;
     	 else
     		 return true;
     
    }
    
	public static ArrayList<RecordWinner> getOldTopTenWinnersAL() {
		return oldTopTenWinnersAL;
	}


	public static void setOldTopTenWinnersAL(ArrayList<RecordWinner> oldTopTenWinnersAL) {
		SysData.oldTopTenWinnersAL = oldTopTenWinnersAL;
	}
	
	 @SuppressWarnings({ "unchecked" })
		public static ArrayList<RecordWinner> initializeTopTen() {
			
	      	//Fill the top 10 with past data about winners:
	    	//read top10 winners from ser file "topTenWinners.ser"
	        try{
	            FileInputStream fis = new FileInputStream(correctedPath + "/topTenWinnersData.ser");
	        	ObjectInputStream ois = new ObjectInputStream(fis);
	        	oldTopTenWinnersAL = (ArrayList<RecordWinner>) ois.readObject();

	            ois.close();
	            fis.close();
	            
	        } 
	        catch (FileNotFoundException  | EOFException f) 
	        {
	      	  //If file not found
	        	System.out.println("Note: this is the first game under this ser file, hence records list is empty. \n");
	            return null;
	        } 
	        catch (IOException i) 
	        {
	        	//if file is empty
	            System.out.println("Note: this is the first game under this ser file, hence records list is empty. \n");
	            return null;
	        	
	        }
	        catch(Exception e) {
	        	//General exception-print it
	        	e.printStackTrace();
	        	return null;
	        }
	        
	        return oldTopTenWinnersAL;
	    	
	    }
	 
	 @SuppressWarnings("unchecked")
	public static void addToTopTen (String username, int score, double time) {
		 
			boolean did_earn_trophy;
			if (score > 200) {
				score = 200;
				did_earn_trophy = true;
			}	
			else if(score == 200) {
				did_earn_trophy = true;
			}
			else
				did_earn_trophy = false;
			

			RecordWinner newPlayerRecord = new RecordWinner(username,score, time, did_earn_trophy);
			ArrayList<RecordWinner> newTopTen = oldTopTenWinnersAL;
			
			// Check if player beat their previous record. If yes, replace with new record, if no, don't do anything.
			if(newTopTen.contains(newPlayerRecord))
			{
				if(newPlayerRecord.compareTo(newTopTen.get(newTopTen.indexOf(newPlayerRecord))) >= 0){
					return;
				}
				else {
					// Remove old record
					newTopTen.remove(newTopTen.get(newTopTen.indexOf(newPlayerRecord)));
					// Add new record
					newTopTen.add(newPlayerRecord);
				}
			} else {
				// Add new record
				newTopTen.add(newPlayerRecord);
			}
			//sort new top 10
			Collections.sort(newTopTen);
			//remove the last (lowest score & time - #11's player) from the winners AL
			if(newTopTen.size() > 10)
				newTopTen.remove(10);
			//write new top 10 to ser file
			try
		      {
					oldTopTenWinnersAL=newTopTen;
		           FileOutputStream fos = new FileOutputStream(correctedPath + "/topTenWinnersData.ser");
		           ObjectOutputStream oos = new ObjectOutputStream(fos);
		           oos.writeObject(newTopTen);
		           oos.close();
		           fos.close();
		       } 
		       catch (IOException ioe) 
		       {
		           ioe.printStackTrace();
		       }
			
	    	
			
			return;
		}

	 
		public static String getThisUser() {
			return thisUser;
		}


		public static void setThisUser(String thisUser) {
			SysData.thisUser = thisUser;
		}


		public static String getPacMode() {
			return pacMode;
		}


		public static void setPacMode(String pacMode) {
			SysData.pacMode = pacMode;
		}


		public static String getGameMode() {
			return gameMode;
		}


		public static void setGameMode(String gameMode) {
			SysData.gameMode = gameMode;
		}
		
		
}
