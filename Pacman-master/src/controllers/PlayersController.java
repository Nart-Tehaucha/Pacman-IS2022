package controllers;

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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import models.Answer;
import models.Player;
import models.Question;

public class PlayersController implements Comparator<Player>, Serializable{

	private static PlayersController playersController = null;

    @FXML
    private ToggleGroup allowNotifications;
    
    @FXML
    private AnchorPane MainPanel;

    @FXML
    private TextField email;

    @FXML
    private TextField nickname;

    @FXML
    private RadioButton no;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField password2;

    @FXML
    private Button saveButton;

    @FXML
    private RadioButton yes;

    @FXML
    private Label enterEmail;
    
    @FXML
    private ImageView goBack;
    
    private ArrayList<Player> allPlayers;
    // Kim
    // ds of tree in order to sort the records of the players
    private TreeSet<Player> sortedPlayersByRecords; 
    

    public PlayersController() {
		super();
		this.allPlayers = new ArrayList<>();
		this.sortedPlayersByRecords = new TreeSet<>();
	}

	public ArrayList<Player> getAllPlayers() {
		return allPlayers;
	}

	public void setAllPlayers(ArrayList<Player> allPlayers) {
		this.allPlayers = allPlayers;
	}

	@FXML
    void Notifications(MouseEvent event) {
    	email.setVisible(true);
    	enterEmail.setVisible(true);
    }
    
    @FXML
    void NoNotif(ActionEvent event) {
    	email.setVisible(false);
    	enterEmail.setVisible(false);
    }
    
    @FXML
    void goToPageBefore(MouseEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginScreen.fxml"));
		LoadScreen(loader);
		return;
    }
   

    @FXML
    void saveData(ActionEvent event) {
    	if(nickname.getText().isEmpty() || password.getText().isEmpty() || password2.getText().isEmpty()
    			|| (!yes.isSelected() && !no.isSelected())){
    		showFailAlert(AlertType.ERROR, "Empty Fields", "Please fill all the fields", null);
    	}
    	if(!password.getText().equals(password2.getText())) {
    		showFailAlert(AlertType.ERROR, "Password don't match!", "Please retype your password", null);
    	}
    	 LoginScreen lc = new LoginScreen();

    	if (!nickname.getText().isEmpty() && !password.getText().isEmpty() && !password2.getText().isEmpty()
			&& (!yes.isSelected() || !no.isSelected()) && (password.getText().equals(password2.getText()))){
    			if(!lc.getNicknamesAndPasswords().containsKey(nickname.getText())) {
    				lc.getNicknamesAndPasswords().put(nickname.getText(), password.getText());

    				showAlert(AlertType.CONFIRMATION, "Successfully Registered", "You have been registerd!", "WELCOME TO PACMAN IS-21");
    				this.getAllPlayers().add(new Player(nickname.getText(), password.getText()));
    				for(Player p : allPlayers) {
    					System.out.println(p.getNickname());
    				}
    				try {
						this.addNewPlayerToJSON(new Player(nickname.getText(), password.getText()));
						//this.readPlayersFromJSON();
						this.deletePlayerFromJSON(new Player(nickname.getText(), password.getText()));
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
			
			if(pls.contains(pl))
				System.out.println("array contains player");
			else
				System.out.println("new player");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
   
    
    @SuppressWarnings("deprecation")
	public static ArrayList<Player> readPlayersFromJSON() throws Exception {
		ArrayList<Player> arrlistp = new ArrayList<>();
		JSONParser jsonParser = new JSONParser();
		
		try {		
			JSONObject obj = (JSONObject) jsonParser.parse(new FileReader("players.json"));
			JSONArray arr = (JSONArray) obj.get("players");
			
			for (Object o : arr) {
				JSONObject playerObj = (JSONObject) o;
				@SuppressWarnings("rawtypes")
				
				String nickname = (String) playerObj.get("nickname");
				String password = (String) playerObj.get("password");
	
				Player p = new Player(nickname, password);
				arrlistp.add(p);
			}
			System.out.println(arrlistp);
		
	    } catch (ParseException | IOException e) {
	        e.printStackTrace();
	    }
		return arrlistp;
	}
    
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static boolean createJSON() throws FileNotFoundException {
		
		JSONArray playersList = new JSONArray();
		JSONObject playersObj = new JSONObject();
		playersObj.put("players", playersList);
		
		
		try(FileWriter fw = new FileWriter("players.json")) {
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
            JSONObject obj = (JSONObject) jsonParser.parse(new FileReader("players.json"));
            
            JSONArray jsonArray = (JSONArray) obj.get("players");
                      
            JSONObject player = new JSONObject();
            player.put("nickname", newPlayer.getNickname());
            player.put("password", newPlayer.getPassword());

            jsonArray.add(player);
            
            FileWriter file = new FileWriter("players.json");
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
   	        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader("players.json"));
   	        JSONArray jsonArray = (JSONArray) jsonObject.get("players");
   	        
   	        JSONArray aux = (JSONArray) jsonArray.clone();
   	        
   	        for(Object o : aux) {
   	        	JSONObject jo = (JSONObject) o;
   	        	if(jo.get("nickname").equals(player.getNickname())) {
	                System.out.println("REMOVING PLAYER:");
	                System.out.println(jo.get("nickname"));
	                jsonArray.remove(o);
   	        	}
   	        }
   	        try (FileWriter file = new FileWriter("players.json")) { //store data
   	            file.write(jsonObject.toJSONString());
   	            file.flush();
   	        }
   	    } catch (IOException | ParseException ex) {
   	        System.out.println("Error: " + ex);
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
	

	public boolean addPlayerToTreeSet(Player player) {
		if(player == null)
			return false;
		if(this.getSortedPlayersByRecords().add(player))
			return true;
		else
			return false;
	}
	
	// comparing players by their score, if their score is equal then by time
	// need to check that it sorts correctly !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
/*	@Override
	public int compare(Player p1, Player p2) {
		// TODO Auto-generated method stub
		if(p1.getRecord().getScore() > p2.getRecord().getScore())
			return 1;
		if(p1.getRecord().getScore() < p2.getRecord().getScore())
			return -1;
		if(p1.getRecord().getScore() == p2.getRecord().getScore()) {
			if(p1.getRecord().getTime() > p2.getRecord().getTime()) 
				return 1;
			else if(p1.getRecord().getTime() < p2.getRecord().getTime())
				return -1;
			else if(p1.getRecord().getTime() == p2.getRecord().getTime())
				return 0;	
		}
		return 0;
	}
*/
	public TreeSet<Player> getSortedPlayersByRecords() {
		return sortedPlayersByRecords;
	}

	public void setSortedPlayersByRecords(TreeSet<Player> sortedPlayersByRecords) {
		this.sortedPlayersByRecords = sortedPlayersByRecords;
	}


	public void writeToFile() {
		
		try {
			FileOutputStream fileOut= new FileOutputStream("Players.ser");
			ObjectOutputStream o = new ObjectOutputStream(fileOut);
		
			o.writeObject(this);
			//o.flush();
			o.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void readFile() {

		try {

			FileInputStream fileIn = new FileInputStream("PlayerSaveFile.ser");
			ObjectInputStream i = new ObjectInputStream(fileIn);
			playersController = (PlayersController) i.readObject();
			i.close();
			fileIn.close();
			
		} catch (FileNotFoundException e) {
			playersController = new PlayersController();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private ObservableList<Player> player = FXCollections.observableArrayList();
	private void load() { // loading the saved scores

		try {
			FileInputStream fis = new FileInputStream("PlayerSaveFile.ser"); // using
																				// serialization
			ObjectInputStream ois = new ObjectInputStream(fis);
			List<Player> list = (List<Player>) ois.readObject();

			player = FXCollections.observableList(list);
			ois.close();
			fis.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Class not found");
			c.printStackTrace();
			return;
		}

	}

	public static void write(ObservableList<Player> list) { // saving the scores to
														// file

		try {

			FileOutputStream fos = new FileOutputStream("PlayerSaveFile.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(new ArrayList<Player>(list));
			oos.close();
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public int compare(Player o1, Player o2) {
		// TODO Auto-generated method stub
		return 0;
	}


}

