package edu.saintjoe.cs.brianc.addsubtractng;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import com.google.devtools.simple.runtime.components.Component;
import com.google.devtools.simple.runtime.components.HandlesEventDispatching;
import com.google.devtools.simple.runtime.components.android.Button;
import com.google.devtools.simple.runtime.components.android.Form;
import com.google.devtools.simple.runtime.components.android.HVArrangement;
import com.google.devtools.simple.runtime.components.android.Label;
import com.google.devtools.simple.runtime.components.android.Sound;
import com.google.devtools.simple.runtime.components.android.TextBox;
import com.google.devtools.simple.runtime.components.android.HorizontalArrangement;

import com.google.devtools.simple.runtime.events.EventDispatcher;

// Adding comment here to test pushing with Git

// Official "base version" for personal project and final exam

// This is Brian Capouch's AddSubtract Code
// Begun 19 March 2014
// Version 4: Memory store and recall 3/28/14
// April 14 first real array-based multi-memory register ops
// April 16: First actual "store in real array memory location" version

public class AddSubtractActivity extends Form implements HandlesEventDispatching {

	// These objects are equivalent to "components" of App Inventor
	
	// We begin with constants, and "global settings variables
	
	// Arithmetic operation constants
	private final int OPERATION_ADD = 1;
	private final int OPERATION_SUBTRACT = 2;
	private final int OPERATION_MULTIPLY = 3;
	private final int OPERATION_DIVIDE = 4;
	
	// Memory-operation constants
	private final boolean MEMORY_STORE = true;
	private final boolean MEMORY_RECALL = false;
	
	// Next two variables control configuration and memory operation
	
	// The number of buttons in the memory row
	private int MEMORY_MAX = 5;
	
	// This variable remembers a "memory mode" state, as either STORE or RETRIEVE
	private boolean memoryStoreMode = MEMORY_STORE;
	
	// Next are the UI widgets
 
	// The containers which organize the app screen
	private HorizontalArrangement line1;
	private HorizontalArrangement line2;
	private HorizontalArrangement line3;
	private HorizontalArrangement line4;
	private HorizontalArrangement line5;
	private HorizontalArrangement line6;
	
	// Which line should be where is a subject for discussion!
	// But here are all the UI widgets
	
	// Widgets for data input
	private Label inputPrompt;
	private TextBox inputBox;
	
	// Calculator operations
	private Button plusButton;
	private Button minusButton;
	private Button timesButton;
	private Button divButton;
	
	// "Main Display" 
	private Label outputLabel;
	
	// This button clears out the calculation memory
	private Button clearCalculation;
	
	// Bottom row has Memory control buttons
	private Button storeButton;
	private Button recallButton;
	private Button memClearButton;
	
	// Here is something we do as scaffolding
	private Label debugLabel;
	
	// We are going to store our buttons in an array
	// BECAUSE it is a simpler way to manage the memory array!!
	private Button[] memButtons = new Button[MEMORY_MAX];
	
	
	// This array contains the current values in the memory registers
	private double[] memoryValue = new double[MEMORY_MAX];
	
 // Java Bridger apps all use $define() in place of main()
 void $define() {
 	
 	// Code in this block is equivalent to the "Designer" part of App Inventor
    
	 // "this" is an Activity object, or the "main screen"
     this.BackgroundColor(COLOR_GRAY);
     
     // Create containers for UI widgets
     line1 = new HorizontalArrangement(this);
     line2 = new HorizontalArrangement(this,LENGTH_FILL_PARENT);
     line3 = new HorizontalArrangement(this);
     line4 = new HorizontalArrangement(this,LENGTH_FILL_PARENT);
     line5 = new HorizontalArrangement(this,LENGTH_FILL_PARENT);
     line6 = new HorizontalArrangement(this,LENGTH_FILL_PARENT);
     
     // "Main Display" on top
     outputLabel = new Label(line1, "0", LENGTH_FILL_PARENT, COLOR_BLACK);
     outputLabel.Height(60);
     outputLabel.FontSize(20.0F);
     outputLabel.TextAlignment(ALIGNMENT_CENTER);
     
     // Widgets for data entry, and "clear calculation" button
     inputPrompt = new Label(line2,"Next value: ",LENGTH_FILL_PARENT, COLOR_BLACK);
     inputBox = new TextBox(line2);
     inputBox.NumbersOnly(true);
     inputBox.TextColor(COLOR_BLACK);
     // Put the reset button up on the top
     clearCalculation = new Button(line2,"Clear");
     
     // Memory control buttons
     storeButton = new Button(line3, "Store Mem");
     recallButton = new Button(line3, "Recall Mem");
     memClearButton = new Button(line3,"Mem Clear");
     
     // Arithmetic operation buttons
     plusButton = new Button(line4, "+", 120);
     minusButton = new Button(line4, "-", 120);
     timesButton = new Button(line4, "*", 120);
     divButton = new Button(line4,"/", 120);
     
     // Initialize our array of memory register buttons
     for (int i = 0 ; i < MEMORY_MAX; i++) {
    	 memButtons[i] = new Button(line5, "M" + i);
     }
     
     // Create the label we will use to debug on the bottom line
     debugLabel = new Label(line6, "Debug output will appear here");
     
     
     // Let the runtime system know which events to report to the dispatcher
     EventDispatcher.registerEventForDelegation(this, "ButtonClick", "Click");
    
 } // end $define()

 // This method, known as a "callback" is invoked by the runtime system
 // It will only be called when a delegated event occurs
 @Override
 public boolean dispatchEvent(Component component, String id, String eventName,
         Object[] args) {
 	
 	// This code is equivalent to the "Blocks" part of App Inventor
 	
 	// Check for calculator operation button presses
     if (component.equals(plusButton) && eventName.equals("Click")){
     	// Program logic gets here when button is clicked
     	stringToDoubleToString(OPERATION_ADD);
         return true;
     }
     if (component.equals(minusButton) && eventName.equals("Click")){
     	// Program logic gets here when button is clicked
     	stringToDoubleToString(OPERATION_SUBTRACT);
         return true;
     }
     if (component.equals(timesButton) && eventName.equals("Click")){
     	// Program logic gets here when button is clicked
     	stringToDoubleToString(OPERATION_MULTIPLY);
         return true;
     }
     if (component.equals(divButton) && eventName.equals("Click")){
     	// Program logic gets here when button is clicked
     	stringToDoubleToString(OPERATION_DIVIDE);
         return true;
     }
     
     // Clear calculation
     if (component.equals(clearCalculation) && eventName.equals("Click")){
     	// Because the code is trivial, the handler is part of the dispatcher here
     	outputLabel.Text("0");
     	inputBox.Text("");
        return true;
     }
     
     // Set global variable controlling memory operations
     if (component.equals(storeButton) && eventName.equals("Click")){
      	// Store into memory
      	memoryStoreMode = MEMORY_STORE;
        return true;
      }
     if (component.equals(recallButton) && eventName.equals("Click")){
       	// Set global memory mode to recall
       	memoryStoreMode = MEMORY_RECALL;
         return true;
       }
     
     // Clear memory registers
     if (component.equals(memClearButton) && eventName.equals("Click")){
    	 // Here again, handler is in dispatcher code
    	 // Clear out all memory registers
    	 for (int i = 0; i < MEMORY_MAX; i++)
    		 memoryValue[i] = 0;
    	 return true;
        } // end if
     
     // Check to see if any of the memory buttons was clicked
     for (int i = 0; i < MEMORY_MAX; i ++ ) {
    	 if (component.equals(memButtons[i]) && eventName.equals("Click")) {
    		 // Call the handler
    		 memoryOp(i);
    		 return true;
    	 	} // endif
     	} // end for
     
     // We return false if the event was not dispatched
     return false;
 } // end dispatchEvent
 
 public void stringToDoubleToString(int operation) {
 	// Main calculator logic method
	 
 	// Here are the operands/result
 	double runTotal, inputValue, temp;
 	temp = 0;
 	
 	// This stuff is dangerous, because the user provides the data	
 	try {
 		// The conversion below may cause a runtime error
 		inputValue = Double.parseDouble(inputBox.Text());
 		}
 	catch (NumberFormatException e) {
 		// Ignore this event!!
 		return;
 		}
 	
 	// This conversion is safe, because the program controls the contents of outputLabel
 	runTotal = Double.parseDouble(outputLabel.Text());
 	
 	// React appropriately, depending on operation selected by user
 	switch(operation) {
 	case OPERATION_ADD:
 		temp = runTotal + inputValue;
 		break;
 	case OPERATION_SUBTRACT:
 		temp = runTotal - inputValue;
 		break;
 	case OPERATION_MULTIPLY:
 		temp = runTotal * inputValue;
 		break;
 	case OPERATION_DIVIDE:
 		// Watch out for those pesky divide-by-zero errors
 		try {
 			temp = runTotal / inputValue;
 			} // end try
 		catch (ArithmeticException kevin_will_memorial_variable) {
 			return;
 			} // end catch
 		break;
 		} // end switch
 	
 	// Convert result back to string and display it
 	outputLabel.Text(String.valueOf(temp));
 	// Clear input box for new input
 	inputBox.Text("");
 	
 	// Scaffolding we used for debugging
 	// outputLabel.Text("I am heading to do operation " + operation);
 	
 } // end stringToIntToString
 
 public void memoryOp(int targetMemCell) {
	 // Logic to store and retrieve from memory array
	 // NOTE TO STUDENTS: Should the "clear memories" operations be moved here?
	 
	 // Store or retrieve according to global mode value
	 if (memoryStoreMode == MEMORY_STORE) {
		 // Store
		 memoryValue[targetMemCell] = Double.parseDouble(outputLabel.Text());
		 debugLabel.Text("I think I am storing into " + targetMemCell);
	 }
		 else {
			 // MEMORY_RECALL
			 inputBox.Text(String.valueOf(memoryValue[targetMemCell])); 
		 }
 } // end memoryOp

 // Scaffolding method used for testing only
 // We will comment this out when in production
public void memTest(int whichButton) {
	 // Scaffold code to test button presses
	 // System.out.println("I am in Memtest");
	 // System.out.println("I have seen button" + whichButton);
	 debugLabel.Text("I am button " + whichButton);
 	} // end memTest
 
} // end class