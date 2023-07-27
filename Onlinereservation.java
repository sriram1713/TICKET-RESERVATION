import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
public class Onlinereservation 
{
	    private static final String USER_DATA_FILE = "userdata.txt";
	    private static HashMap<String, UserData> userData = new HashMap<>();
    	private static UserData currentUser;
    	private static HashSet<Integer> usedTicketNumbers = new HashSet<>();
    	public static void main(String args[]) 
	    {
        	loadUserDataFromFile();
            
	        JFrame f = new JFrame("Login/Register Example");
        	final JLabel label = new JLabel();
        	label.setBounds(20, 200, 350, 50);
        	final JPasswordField value = new JPasswordField();
        	value.setBounds(100, 75, 100, 30);
        	JLabel l1 = new JLabel("Username:");
        	l1.setBounds(20, 20, 80, 30);
        	JLabel l2 = new JLabel("Password:");
        	l2.setBounds(20, 75, 80, 30);
        	JButton b = new JButton("Login");
        	b.setBounds(100, 150, 80, 30);
        	JButton registerButton = new JButton("Register");
        	registerButton.setBounds(200, 150, 100, 30);
        	final JTextField text = new JTextField();
        	text.setBounds(100, 20, 100, 30);
        	f.add(value);
        	f.add(l1);
        	f.add(label);
        	f.add(l2);
        	f.add(b);
        	f.add(registerButton);
        	f.add(text);
        	f.setSize(400, 300);
        	f.setLayout(null);
        	f.setVisible(true);
        	b.addActionListener(new ActionListener() 
		    {
            		public void actionPerformed(ActionEvent e) 
			        {
                		String username = text.getText();
                		String password = new String(value.getPassword());
		                if (userData.containsKey(username) && userData.get(username).getPassword().equals(password)) 
				        {
                    			currentUser = userData.get(username);
                    			showDetailsEntryForm(f, label);
                		} 
				        else 
				        {
                    			label.setText("Invalid username or password!");
                		}
            		}
        	});
        	registerButton.addActionListener(new ActionListener() 
		    {
            		public void actionPerformed(ActionEvent e) 
			        {
                		String username = text.getText();
                		String password = new String(value.getPassword());
	                	if (userData.containsKey(username)) 
				        {
                    			label.setText("Username already exists!");
                		} 
				        else 
				        {
                   			UserData newUser = new UserData(username, password);
                   	 		userData.put(username, newUser);
                    			saveUserDataToFile();
                    			currentUser = newUser;
                    			showDetailsEntryForm(f, label);
                		}
            		}
        	});
    	}
    	private static void showDetailsEntryForm(JFrame f,JLabel label) 
    	{
        	f.getContentPane().removeAll();
        	f.repaint();
	        JLabel sourceLabel = new JLabel("Source:");
        	sourceLabel.setBounds(20, 20, 80, 30);
        	JLabel destinationLabel = new JLabel("Destination:");
        	destinationLabel.setBounds(20, 70, 80, 30);
        	JLabel dateLabel = new JLabel("Date of Journey:");
        	dateLabel.setBounds(20, 120, 100, 30);
        	JTextField sourceField = new JTextField();
        	sourceField.setBounds(120, 20, 100, 30);
        	JTextField destinationField = new JTextField();
        	destinationField.setBounds(120, 70, 100, 30);
        	JTextField dateField = new JTextField();
        	dateField.setBounds(120, 120, 100, 30);
		    dateField.setText("DD-MM-YYYY");
        	JButton saveButton = new JButton("Save");
        	saveButton.setBounds(100, 180, 80, 30);
		    JLabel sourceErrorLabel = new JLabel();
    		sourceErrorLabel.setBounds(230, 20, 150, 30);
    		JLabel destinationErrorLabel = new JLabel();
    		destinationErrorLabel.setBounds(230, 70, 150, 30);
    		JLabel dateErrorLabel = new JLabel();
    		dateErrorLabel.setBounds(230, 120, 150, 30);
		    JLabel ticketLabel=new JLabel("Ticket Number");
		    ticketLabel.setBounds(20,230,100,30);
		    JLabel ticketNumberLabel=new JLabel();
		    ticketNumberLabel.setBounds(120,230,100,30);
		    if (currentUser.getTicketNumber() > 0) 
		    {
        		JButton cancelButton = new JButton("Cancel Ticket");
        		cancelButton.setBounds(200, 180, 150, 30);
        		f.add(cancelButton);
        		JLabel cancelLabel = new JLabel("Enter Ticket Number to Cancel:");
        		cancelLabel.setBounds(20, 270, 200, 30);
			    JTextField cancelNumberField = new JTextField();
        		cancelNumberField.setBounds(220, 270, 100, 30);
			    JButton cancelButtonSubmit = new JButton("Submit");
        		cancelButtonSubmit.setBounds(100, 320, 80, 30);
	        	JLabel cancelResultLabel = new JLabel();
	        	cancelResultLabel.setBounds(20, 370, 400, 30);
			    f.add(cancelLabel);
        		f.add(cancelNumberField);
        		f.add(cancelButtonSubmit);
        		f.add(cancelResultLabel);
			    cancelButton.addActionListener(new ActionListener() 
			    {
        	    		public void actionPerformed(ActionEvent e) 
		    		    {
                			// Show ticket cancellation form
                			cancelButton.setVisible(false);
                			cancelLabel.setVisible(true);
                			cancelNumberField.setVisible(true);
                			cancelButtonSubmit.setVisible(true);
                			cancelResultLabel.setVisible(true);
            			}
        		});
			    cancelButtonSubmit.addActionListener(new ActionListener() 
			    {
            			public void actionPerformed(ActionEvent e) 
				        {
                			// Perform ticket cancellation
                			int cancelTicketNumber = Integer.parseInt(cancelNumberField.getText());
                			if (cancelTicketNumber == currentUser.getTicketNumber()) 
					        {
                    				currentUser.setTicketNumber(0);
                    				saveUserDataToFile();
                    				cancelResultLabel.setText("Your ticket number " + cancelTicketNumber + " was cancelled.");
               	 			} 
					        else 
					        {
                    				cancelResultLabel.setText("Invalid ticket number! Please enter the correct ticket number.");
                			}
            			}
        		});
    		}
		    f.add(sourceLabel);
		    f.add(destinationLabel);
        	f.add(dateLabel);
        	f.add(sourceField);
        	f.add(destinationField);
       		f.add(dateField);
        	f.add(saveButton);
		    f.add(sourceErrorLabel); 
    		f.add(destinationErrorLabel);
    		f.add(dateErrorLabel);
		    f.add(ticketLabel);
		    f.add(ticketNumberLabel);
        	saveButton.addActionListener(new ActionListener() 
		    {
        	    public void actionPerformed(ActionEvent e) 
		        {
                	String source = sourceField.getText();
                	String destination = destinationField.getText();
                	String date = dateField.getText();
			        sourceErrorLabel.setText("");
            		destinationErrorLabel.setText("");
            		dateErrorLabel.setText("");
			        boolean hasError = false;
        	    	if (source.isEmpty()) 
			        {
                		sourceErrorLabel.setText("Source is required!");
                		hasError = true;
            		}
        	    	if (destination.isEmpty()) 
			        {   
                		destinationErrorLabel.setText("Destination is required!");
                		hasError = true;
            		}
			        if (date.isEmpty() || date.equals("DD-MM-YYYY")) 
			        {
                		dateErrorLabel.setText("Date of journey is required!");
                		hasError = true;
            		} 
			        else 
			        {
                		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                		try 
				        {
                    			Date journeyDate = sdf.parse(date);
                    			currentUser.setDateOfJourney(sdf.format(journeyDate));
                		} 
				        catch (Exception ex) 
				        {
                    			dateErrorLabel.setText("Invalid date format! Use yyyy-MM-dd");
                    			hasError = true;
                		}
            		}
			        if (source.equals(destination)) 
			        {
                		sourceErrorLabel.setText("Source and destination cannot be the same!");
                		destinationErrorLabel.setText("Source and destination cannot be the same!");
                		hasError = true;
            		}
		
                	if (!hasError) 
			        {
                		// Generate unique 6-digit ticket number
                		int ticketNumber = generateTicketNumber();
                		currentUser.setTicketNumber(ticketNumber);
                		saveUserDataToFile();
	
 		       	        // Display the Ticket number below the Save button
                		ticketNumberLabel.setText(String.valueOf(ticketNumber));
		
                		label.setText("Details saved for user: " + currentUser.getUsername() + ", Ticket Number: " + ticketNumber);
            		}
                }
		    });
		    f.setSize(400,300);
    	}
    	private static int generateTicketNumber() 
    	{
        	Random random = new Random();
        	int ticketNumber = 0;
        	while (ticketNumber < 100000 || usedTicketNumbers.contains(ticketNumber)) 
        	{
            		ticketNumber = random.nextInt(900000) + 100000; // Generates a 6-digit number
        	}
        	usedTicketNumbers.add(ticketNumber);
        	return ticketNumber;
    	}
    	private static void loadUserDataFromFile() 
    	{
        	try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) 
		    {
            		String line;
            		while ((line = reader.readLine()) != null) 
			        {
                		String[] parts = line.split(",");
                		if (parts.length == 6) 
				        {
                    			String username = parts[0];
                    			String password = parts[1];
                    			String source = parts[2];
                    			String destination = parts[3];
                    			String date = parts[4];
                    			int ticketNumber = Integer.parseInt(parts[5]);
                    			UserData user = new UserData(username, password, source, destination, date, ticketNumber);
                    			userData.put(username, user);
                    			usedTicketNumbers.add(ticketNumber);
                		}
            		}
        	}
		    catch (IOException e) 
		    {
            // Error reading file, handle if needed
        	}
    	}
    	private static void saveUserDataToFile() 
	    {
        	try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE))) 
		    {
            		for (UserData user : userData.values()) 
			        {
                		writer.write(user.toString());
                		writer.newLine();
            		}
        	} 
		    catch (IOException e) 
		    {
		    }
    	}
	    static class UserData 
	    {
        	private String username;
        	private String password;
        	private String source;
        	private String destination;
        	private String dateOfJourney;
        	private int ticketNumber;
	        public UserData(String username, String password) 
		    {
            		this.username = username;
            		this.password = password;
        	}
	        public UserData(String username, String password, String source, String destination, String dateOfJourney, int ticketNumber) 
		    {
            		this(username, password, source, destination, dateOfJourney);
            		this.ticketNumber = ticketNumber;
        	}
        	public UserData(String username, String password, String source, String destination, String dateOfJourney) 
		    {
            		this(username, password);
            		this.source = source;
            		this.destination = destination;
            		this.dateOfJourney = dateOfJourney;
        	}
        	public String getUsername() 
		    {   
            		return username;
        	}
        	public String getPassword() 
		    {
            		return password;
        	}
	        public String getSource() 
		    {
            		return source;
        	}
        	public void setSource(String source) 
		    {
            		this.source = source;
        	}
        	public String getDestination() 
		    {
            		return destination;
        	}
		    public void setDestination(String destination) 
		    {   
            		this.destination = destination;
        	}
	        public String getDateOfJourney() 
		    {
            		return dateOfJourney;
        	}
        	public void setDateOfJourney(String dateOfJourney) 
		    {
            		this.dateOfJourney = dateOfJourney;
        	}
        	public int getTicketNumber() 
		    {
            		return ticketNumber;
        	}
	        public void setTicketNumber(int ticketNumber) 
		    {
            		this.ticketNumber = ticketNumber;
        	}
        	@Override
        	public String toString() 
		    {
            		return username + "," + password + "," + source + "," + destination + "," + dateOfJourney + "," + ticketNumber;
            }
        }
}

