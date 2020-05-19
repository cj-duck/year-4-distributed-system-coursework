import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.TextField;
import java.awt.Choice;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddProductDialog extends JDialog {
	
	static StoreApplicationLayer appLayer;

	private final JPanel contentPanel = new JPanel();

	public AddProductDialog() {
		
		StoreDataLayer dataLayer = new StoreDataLayer();
		appLayer = new StoreApplicationLayer(dataLayer);
		setBounds(100, 100, 287, 272);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblProductName = new JLabel("Product Name:");
			lblProductName.setBounds(10, 36, 108, 22);
			contentPanel.add(lblProductName);
		}
		{
			JLabel lblProductPrice = new JLabel("Product Price:");
			lblProductPrice.setBounds(10, 78, 108, 22);
			contentPanel.add(lblProductPrice);
		}
		{
			JLabel lblProductQuantity = new JLabel("Product Quantity:");
			lblProductQuantity.setBounds(10, 122, 108, 22);
			contentPanel.add(lblProductQuantity);
		}
		
		TextField textProdName = new TextField();
		textProdName.setBounds(124, 36, 137, 22);
		contentPanel.add(textProdName);
		
		TextField textProdPrice = new TextField();
		textProdPrice.setBounds(124, 78, 137, 22);
		contentPanel.add(textProdPrice);
		
		TextField textProdQuantity = new TextField();
		textProdQuantity.setBounds(124, 122, 137, 22);
		contentPanel.add(textProdQuantity);
		
		Choice choiceProdPromo = new Choice();
		choiceProdPromo.setBounds(124, 166, 137, 20);
		choiceProdPromo.add("None");
		choiceProdPromo.add("3 for 2");
		choiceProdPromo.add("Buy 1 get 1 free");
		choiceProdPromo.add("Free delivery");
		contentPanel.add(choiceProdPromo);
		
		JLabel lblProductPromo = new JLabel("Product Promo:");
		lblProductPromo.setBounds(10, 166, 108, 20);
		contentPanel.add(lblProductPromo);
		
		JLabel lblAddProduct = new JLabel("Add Product");
		lblAddProduct.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAddProduct.setBounds(98, 11, 86, 14);
		contentPanel.add(lblAddProduct);
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
						String name = textProdName.getText();
						int price = Integer.parseInt(textProdPrice.getText());
						int quantity = Integer.parseInt(textProdQuantity.getText());
						String promo = choiceProdPromo.getSelectedItem();
						String result = appLayer.addProduct(name, price, quantity, promo);
						System.out.println(result);
						dispose();
						} catch (NumberFormatException ne) {
							JOptionPane.showMessageDialog(contentPanel,
									"Please ensure price and quantity inputs are integers and no inputs are left blank.",
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
