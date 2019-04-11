package db;


import java.lang.Object;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Container;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class JavaDB {



    public static SqliteDB db = new SqliteDB();
    public static Object[] row = new Object[4];
    public static DefaultTableModel model = new DefaultTableModel();

    public static void main(String args[]) {

        JTable table = new JTable(); 
        JFrame frame = new JFrame();
        frame.setSize(680,400);
        frame.setTitle("Database SQLite");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create a table with Students to database
        db.createNewTable();

        //create a table model and set Columns 
        Object[] columns = {"ID","First Name","Last Name"};
        model.setColumnIdentifiers(columns);
        //set the model to the table
        table.setModel(model);

        //height of the row
        table.setRowHeight(30);

        // create JTextFields
        JTextField textId = new JTextField();
        JTextField textFname = new JTextField();
        JTextField textLname = new JTextField();


        //create JButtons
        JButton btnAdd = new JButton("Add");  
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");

        //setiing textfields
        textId.setBounds(20, 240, 100, 25);
        textFname.setBounds(20, 270, 100, 25);
        textLname.setBounds(20, 300, 100, 25);

        //usage: setBounds(x, y, width, height)
        btnAdd.setBounds(150, 220, 100, 25);
        btnUpdate.setBounds(150, 265, 100, 25);
        btnDelete.setBounds(150, 310, 100, 25);

        //create JScrollPane
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 680, 400);

        frame.setLayout(null);

        //add textfields to frame
        frame.add(textId);
        frame.add(textFname);
        frame.add(textLname);


        //add buttons to frame
        frame.add(btnAdd);
        frame.add(btnDelete);
        frame.add(btnUpdate);

        frame.setVisible(true);
        frame.add(pane);
        //refresh on the start
        db.selectAll();

        //update record to database
        btnUpdate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                db.update(Integer.parseInt(textId.getText()), textFname.getText(), textLname.getText());

                //refresh after update
                //remove all rows
                int rowCount = model.getRowCount();
                if (rowCount != 0) 
                    for (int i = rowCount - 1; i >= 0; i--) {
                        model.removeRow(i);
                    }
                //show database
                db.selectAll();
            }
        });

        //add record to database
        btnAdd.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                //adding    
                db.insert(textFname.getText(), textLname.getText());

                //get last id from database
                row[0] = db.getLastId();

                //adding data to rows
                row[1] = textFname.getText();
                row[2] = textLname.getText();

                //add row to the model
                model.addRow(row);
            }
        });

        //delete record from database
        btnDelete.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                //i is the index of the selected row
                 int i = table.getSelectedRow();
                 if(i >= 0){
                    //remove a row from the table
                    model.removeRow(i);
                    //removing from db
                    db.delete(Integer.parseInt(textId.getText()));
                }
            }  
        });

        //setting the textfields by selected row
        table.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e){

                //i is the index of the selected row
                int i = table.getSelectedRow();

                //setting
                textId.setText(model.getValueAt(i, 0).toString());
                textFname.setText(model.getValueAt(i, 1).toString());
                textLname.setText(model.getValueAt(i, 2).toString());

            }
            });

        //closing connection to the database
        db.closeConnection();

    }
    
    //getting row from database and setting row to the table
    public static void setMessagesArea(String[] strArray) {

        row[0] = strArray[0];
        row[1] = strArray[1];
        row[2] = strArray[2];
        model.addRow(row);
    }
}
