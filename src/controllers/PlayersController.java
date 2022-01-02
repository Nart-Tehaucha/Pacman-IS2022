package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import models.Answer;
import models.Question;

// controls the new player screen, implements Serializable in order to retrieve all the data of the players
// implemets Comparator
public class PlayersController implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private static PlayersController playersController = null;

	 @FXML
	    private AnchorPane MainPanel;

	    @FXML
	    private ImageView goBack;

	    @FXML
	    private TextField nickname;

	    @FXML
	    private PasswordField password;

	    @FXML
	    private PasswordField password2;

	    @FXML
	    private Button saveButton;
	    
	    @FXML
	    private Text passwordMeter;

    
    private ArrayList<Player> allPlayers;
    
    public PlayersController() {
		super();
		this.allPlayers = new ArrayList<>();
	}

	public ArrayList<Player> getAllPlayers() {
		return allPlayers;
	}

	public void setAllPlayers(ArrayList<Player> allPlayers) {
		this.allPlayers = allPlayers;
	}

    @FXML
    void goToPageBefore(MouseEvent event) {
    	if(SysData.getThisUser().equals("admin")) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AdminMenu.fxml"));
			LoadScreen(loader);
			return;
    	}
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginScreen.fxml"));
		LoadScreen(loader);
		return;
    }

    @FXML
    void saveData(ActionEvent event) {
    	if(nickname.getText().isEmpty() || password.getText().isEmpty() || password2.getText().isEmpty()){
    		showFailAlert(AlertType.ERROR, "Empty Fields", "Please fill all the fields", null);
    	}
    	if(!password.getText().equals(password2.getText())) {
    		showFailAlert(AlertType.ERROR, "Password don't match!", "Please retype your password", null);
    	}
    	 LoginScreen lc = new LoginScreen();

    	if (!nickname.getText().isEmpty() && !password.getText().isEmpty() && !password2.getText().isEmpty() && (password.getText().equals(password2.getText()))){
    			if(!lc.getNicknamesAndPasswords().containsKey(nickname.getText())) {
    				SysData.setThisUser(nickname.getText());
    				lc.getNicknamesAndPasswords().put(nickname.getText(), password.getText());

    				showAlert(AlertType.CONFIRMATION, "Successfully Registered", "You have been registerd!", "");
    				this.getAllPlayers().add(new Player(nickname.getText(), password.getText()));

    				try {
						PlayersController.addNewPlayerToJSON(new Player(nickname.getText(), password.getText()));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
					LoadScreen(loader);
					return;
    			}
    			else
    				showFailAlert(AlertType.ERROR, "Nickname already exists!", "Oops... it seems like this exact nickname has been taken", null);
		}
		try {
			ArrayList<Player> pls = readPlayersFromJSON();
			Player pl = new Player(nickname.getText(),password.getText());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	// ** if the password is less than 4 characters than says its weak ** //
    @FXML
    void passwordStrength(KeyEvent event) {
    	if (password.getText().length() < 4) {
			passwordMeter.setText("Weak");
			passwordMeter.setStyle(".text { \r\n" + " -fx-font-smoothing-type: lcd;\r\n" + " -fx-fill: red;\r\n"
				+ " -fx-font-weight: bold; " + "}");

		}
		if (password.getText().length() >= 4 && password.getText().length() < 6) {
			passwordMeter.setText("Medium");
			passwordMeter.setStyle(".text { \r\n" + " -fx-font-smoothing-type: lcd;\r\n" + " -fx-fill: orange;\r\n"
				+ " -fx-font-weight: bold; " + "}");

		}
		else if(password.getText().length() >= 6) {
			passwordMeter.setText("Strong");
			passwordMeter.setStyle(".text { \r\n" + " -fx-font-smoothing-type: lcd;\r\n" + " -fx-fill: green;\r\n"
				+ " -fx-font-weight: bold; " + "}");
		}
    }

    public static ArrayList<Player> readPlayersFromJSON() throws Exception {
    	
    	
		ArrayList<Player> arrlistp = new ArrayList<>();
		JSONParser jsonParser = new JSONParser();
		
		try {		
			JSONObject obj = (JSONObject) jsonParser.parse(new FileReader(SysData.correctedPath + "/players.json"));
			JSONArray arr = (JSONArray) obj.get("players");
			
			for (Object o : arr) {
				JSONObject playerObj = (JSONObject) o;
				String nickname = (String) playerObj.get("nickname");
				String password = (String) playerObj.get("password");
	
				Player p = new Player(nickname, password);
				arrlistp.add(p);
			}
		
	    } catch (ParseException | IOException e) {
	        e.printStackTrace();
	    }
		return arrlistp;
	}
    
	@SuppressWarnings({ "unchecked" })
	public static boolean createJSON() throws FileNotFoundException {
		
		JSONArray playersList = new JSONArray();
		JSONObject playersObj = new JSONObject();
		playersObj.put("players", playersList);
		
		
		try(FileWriter fw = new FileWriter(SysData.correctedPath + "/players.json")) {
			fw.write(playersObj.toJSONString());
			fw.flush();
		}
		catch(IOException i) {
			i.printStackTrace();
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean addNewPlayerToJSON(Player newPlayer) {
		JSONParser jsonParser = new JSONParser();

        try {
            JSONObject obj = (JSONObject) jsonParser.parse(new FileReader(SysData.correctedPath + "/players.json"));
            
            JSONArray jsonArray = (JSONArray) obj.get("players");
                      
            JSONObject player = new JSONObject();
            player.put("nickname", newPlayer.getNickname());
            player.put("password", newPlayer.getPassword());

            jsonArray.add(player);
            
            FileWriter file = new FileWriter(SysData.correctedPath + "/players.json");
            file.write(obj.toJSONString());
            file.flush();
            file.close();
            
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return true;
    }
	
    public static void deletePlayerFromJSON(Player player) {
   	 try {
   	        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader(SysData.correctedPath + "/players.json"));
   	        JSONArray jsonArray = (JSONArray) jsonObject.get("players");
   	        
   	        JSONArray aux = (JSONArray) jsonArray.clone();
   	        
   	        for(Object o : aux) {
   	        	JSONObject jo = (JSONObject) o;
   	        	if(jo.get("nickname").equals(player.getNickname())) {
	                jsonArray.remove(o);
   	        	}
   	        }
   	        try (FileWriter file = new FileWriter(SysData.correctedPath + "/players.json")) { //store data
   	            file.write(jsonObject.toJSONString());
   	            file.flush();
   	        }
   	    } catch (IOException | ParseException ex) {
   	        ex.getStackTrace();
   	    }
   }
    
    @FXML
	void LoadScreen(FXMLLoader loader) {
		try {

			AnchorPane pane = loader.load();
			MainPanel.getChildren().clear();
			MainPanel.getChildren().add(pane);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void showAlert(AlertType type, String title, String header, String text) {
		
		Alert alert = new Alert(type);

		alert.setHeight(Region.USE_PREF_SIZE);
		alert.setWidth(Region.USE_PREF_SIZE);
		alert.setResizable(true);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(text);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setStyle(
				"-fx-background-image: url('/views/bg.jpg'); -fx-background-size: cover; -fx-font-weight: bold; -fx-font-size: 12px;");
		String path = new File(SysData.correctedPath + "/Pacman-master/src/media/success.mp3").getAbsolutePath();
		MediaPlayer sound = new MediaPlayer(new Media(new File(path).toURI().toString()));
		sound.play();
		alert.showAndWait();
	}

	private void showFailAlert(AlertType type, String title, String header, String text) {
		Alert alert = new Alert(type);

		alert.setHeight(Region.BASELINE_OFFSET_SAME_AS_HEIGHT);
		alert.setWidth(Region.BASELINE_OFFSET_SAME_AS_HEIGHT);
		alert.setResizable(true);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}

}

