import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.TextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddCustomerDialog extends JDialog {
	
	static StoreApplicationLayer appLayer;

	private final JPanel contentPanel = new JPanel();

	public AddCustomerDialog() {
		
		StoreDataLayer dataLayer = new StoreDataLayer();
		appLayer = new StoreApplicationLayer(dataLayer);
		setBounds(100, 100, 288, 188);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblCustomerName = new JLabel("First Name:");
			lblCustomerName.setBounds(10, 36, 108, 22);
			contentPanel.add(lblCustomerName);
		}
		{
			JLabel lblProductPrice = new JLabel("Second Name:");
			lblProductPrice.setBounds(10, 78, 108, 22);
			contentPanel.add(lblProductPrice);
		}
		
		TextField textCustFirst = new TextField();
		textCustFirst.setBounds(124, 36, 137, 22);
		contentPanel.add(textCustFirst);
		
		TextField textCustSecond = new TextField();
		textCustSecond.setBounds(124, 78, 137, 22);
		contentPanel.add(textCustSecond);
		
		JLabel lblAddCustomer = new JLabel("Add Customer");
		lblAddCustomer.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAddCustomer.setBounds(84, 11, 148, 14);
		contentPanel.add(lblAddCustomer);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton addProduct = new JButton("Add");
				buttonPane.add(addProduct);
				getRootPane().setDefaultButton(addProduct);
				
				addProduct.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try{
						String firstName = textCustFirst.getText();
						String secondName = textCustSecond.getText();
						int loyalty = 0;
						String result = appLayer.addCustomer(firstName, secondName, loyalty);
						System.out.println(result);
						dispose();
						} catch (NumberFormatException ne) {
							JOptionPane.showMessageDialog(contentPanel,
									"Please ensure no inputs are left blank.",
									"Warning", JOptionPane.WARNING_MESSAGE);
						}
					}
				});
			}
			{
				JButton cancelAddProductButton = new JButton("Cancel");
				cancelAddProductButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelAddProductButton.setActionCommand("Cancel");
				buttonPane.add(cancelAddProductButton);
			}
		}			
	}
}
