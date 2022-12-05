// Maximilian Kaczmarek - UCID 30151219
// Cpsc 231 Tutorial 1 
// April 1 2022

package mvh.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import mvh.enums.WeaponType;
import mvh.world.*;

public class MainController {

	public static Main mainApp;
	
    //Store the data of editor
    private World world;

    @FXML
    private Button addHeroButton;

    @FXML
    private Button addMonsterButton;

    @FXML
    private TextField columnsTextfield;

    @FXML
    private Label currentActivity;

    @FXML
    private Label currentStatus;

    @FXML
    private TextArea entityDetails;

    @FXML
    private Menu helpButton;

    @FXML
    private TextField heroArmorTextfield;

    @FXML
    private CheckBox heroCheckbox;

    @FXML
    private TextField heroHealthTextfield;

    @FXML
    private TextField heroSymbolTextfield;

    @FXML
    private TextField heroWeaponTextfield;

    @FXML
    private MenuItem loadButton;

    @FXML
    private CheckBox monsterCheckbox;

    @FXML
    private TextField monsterHealthTextfield;

    @FXML
    private TextField monsterSymbolTextfield;

    @FXML
    private ChoiceBox<Character> monsterWeaponDropdown;

    @FXML
    private Button newWorldButton;

    @FXML
    private MenuItem quitButton;

    @FXML
    private TextField rowsTextfield;

    @FXML
    private TextField rowToView;
    
    @FXML
    private TextField columnToView;

    @FXML
    private Button viewButton;
    
    @FXML
    private TextField heroRowLocation;
    
    @FXML
    private TextField heroColumnLocation;
    
    @FXML
    private TextField monsterRowLocation;
    
    @FXML
    private TextField monsterColumnLocation;
    
    @FXML
    private Label noWorldLabel;
    
    /**
     * Setup the window state upon first opening application
     */
    @FXML
    public void initialize() {
    	currentStatus.setText("Load/create world to continue");
    	currentActivity.setText("Start editing world");
    	
    	ArrayList<Character> weaponTypes = new ArrayList();
    	
    	weaponTypes.add('C');
    	weaponTypes.add('A');
    	weaponTypes.add('S');
    	
    	// setting the values in the dropdown
    	monsterWeaponDropdown.setItems(FXCollections.observableArrayList(weaponTypes));
    	
    }

    /**
     * Method for connecting classes and accessibility
     * @param main
     */
	public void linkWithApplication(Main main) {
		MainController.mainApp = main;
	}
	
	/**
	 * Method which calls aboutView from main to display about info
	 */
	public void showAboutInfo() {
		mainApp.aboutView();
	}
	
	public void loadFile() throws FileNotFoundException {
		// code taken from Reader in A2
		File file = new File("src/main/java/world.txt");
        Scanner input;

        input = new Scanner(file);

        int numberOfRows = Integer.parseInt(input.nextLine());
        int numberOfColumns = Integer.parseInt(input.nextLine());
        
        rowsTextfield.setText(String.valueOf(numberOfRows));  
        columnsTextfield.setText(String.valueOf(numberOfColumns));
        
        world = new World(numberOfRows, numberOfColumns);

        for (int i = 0; i < (numberOfColumns * numberOfRows); i++) {
            String[] infoToProcess = input.nextLine().split(",");

            int rowCoordinate = Integer.parseInt(infoToProcess[0]);
            int columnCoordinate = Integer.parseInt(infoToProcess[1]);

            if (infoToProcess.length == 2) {
                world.addEntity(rowCoordinate, columnCoordinate, null);
            }

            else if (infoToProcess.length > 2) {
                String entityType = infoToProcess[2];
                char entitySymbol = infoToProcess[3].charAt(0);
                int entityHealth = Integer.parseInt(infoToProcess[4]);

                if (entityType.equals("MONSTER")) {
                    String monsterWeaponType = infoToProcess[5];

                    world.addEntity(rowCoordinate, columnCoordinate, new Monster(entityHealth, entitySymbol, WeaponType.getWeaponType(monsterWeaponType.charAt(0))));
                    
                }
                else if (entityType.equals("HERO")) {
                    int weaponStrength = Integer.parseInt(infoToProcess[5]);
                    int armorStrength = Integer.parseInt(infoToProcess[6]);

                    world.addEntity(rowCoordinate, columnCoordinate, new Hero(entityHealth, entitySymbol, weaponStrength, armorStrength ));
                }
            }
        }
        
        currentActivity.setText("Viewing world");
        currentStatus.setText("Loaded world!");
	}
	
	/**
	 * Method which writes to a file after world has been created through the GUI
	 * @param event
	 */
	public void saveFile(ActionEvent event) {
		// TO-DO
	}
	
    @FXML
    void createNewWorld(ActionEvent event) {
    	world = new World(Integer.parseInt(rowsTextfield.getText()), Integer.parseInt(columnsTextfield.getText()));
    	Entity[][] newWorld = new Entity[Integer.parseInt(rowsTextfield.getText())][Integer.parseInt(columnsTextfield.getText())];
    	
    	currentStatus.setText("Created world!");
    	currentActivity.setText("Created world!");
    	noWorldLabel.setText("");
    }
    
    /**
     * Method to go in the world 2d array and locate specified entity and return its info
     * @param event
     */
    @FXML
    void searchForEntity(ActionEvent event) {
    	currentActivity.setText("Viewing entity");
    	
    	Entity viewing = world.getEntity(Integer.parseInt(rowToView.getText()), Integer.parseInt(columnToView.getText()));
    	
    	if (viewing == null) {
    		entityDetails.setText("No entity exists at this location");
    		currentStatus.setText("No entity found!");
    		currentActivity.setText("Viewing entity");
    	}
    	
    	String entityInfo = viewing.toString();
    	String[] info = entityInfo.split("\t");
    	entityDetails.setText("Type: " + info[0] + "\nSymbol: " + info[1] + "\nHealth: " + info[2] + "\nState: " + info[3]);
    	currentStatus.setText("Found entity!");
    }
    
    /**
     * Method which deletes entity (replaces with null) in world
     * @param event
     */
    @FXML
    void deleteEntity(ActionEvent event) {
    	Entity deleting = world.getEntity(Integer.parseInt(rowToView.getText()), Integer.parseInt(columnToView.getText()));
    	if (deleting == null) {
    		entityDetails.setText("No entity exists at this location!");
    		currentStatus.setText("No entity found!");
    	}
    	else {
    		deleting = null;
    		entityDetails.setText("No entity exists at this location now");
    		currentStatus.setText("Deleted entity");
    	}
    }
    
	/**
	 * Method that handles add hero button
	 * @param event
	 */
	public void addHero(ActionEvent event) {
		Hero myHero = new Hero(Integer.parseInt(heroHealthTextfield.getText()), heroSymbolTextfield.getText().charAt(0), Integer.parseInt(heroWeaponTextfield.getText()), Integer.parseInt(heroArmorTextfield.getText()));
		world.addEntity(Integer.parseInt(heroRowLocation.getText()) ,Integer.parseInt(heroColumnLocation.getText()),myHero);
		currentStatus.setText("Success!");
		currentActivity.setText("Added hero!");
		entityDetails.setText("Type: Hero" + "\nHealth: " + heroHealthTextfield.getText() + "\nSymbol: " + heroSymbolTextfield.getText() + "\nWeapon strength: " + heroWeaponTextfield.getText() + "\nArmor strength: " + heroArmorTextfield.getText());
	}
	
	/**
	 * Method that handles add monster button
	 * @param event
	 */
	public void addMonster(ActionEvent event) {
		Monster myMonster = new Monster(Integer.parseInt(monsterHealthTextfield.getText()), monsterSymbolTextfield.getText().charAt(0), WeaponType.getWeaponType(monsterWeaponDropdown.getValue()) );
		world.addEntity(Integer.parseInt(monsterRowLocation.getText()) ,Integer.parseInt(monsterColumnLocation.getText()), myMonster);
		currentStatus.setText("Success!");
		currentActivity.setText("Added monster!");
		entityDetails.setText("Type: Monster" + "\nSymbol: " + monsterSymbolTextfield.getText().charAt(0) + "\nHealth: " + Integer.parseInt(monsterHealthTextfield.getText()) + "\nWeapon " + monsterWeaponDropdown.getValue());
	}
	
	/**
	 * Method that updates activity when adding monster
	 * @param event
	 */
    @FXML
    public void monsterActivity(ActionEvent event) {
    	currentActivity.setText("Adding monster");
    }

    /**
     * Method that updates activity when adding hero
     * @param event
     */
    @FXML
    public void heroActivity(ActionEvent event) {
    	currentActivity.setText("Adding hero");
    }
    
	/**
	 * Method which closes application on action event (quit button)
	 */
	public void quit(ActionEvent event) {
		Platform.exit();
	}
}
