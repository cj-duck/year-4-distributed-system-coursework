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
import java.awt.Toolkit;

public class FoundProductDialog extends JDialog {

	static StoreApplicationLayer appLayer;

	private final JPanel contentPanel = new JPanel();

	public FoundProductDialog(Product foundProduct) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				"C:\\Users\\Chris\\Desktop\\Coursework\\lib\\favicon.png"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.DOCUMENT_MODAL);
		setModal(true);

		String name = foundProduct.getName();
		String price = Integer.toString(foundProduct.getPrice());
		String quantity = Integer.toString(foundProduct.getQuantity());
		String promo = foundProduct.getPromo();

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
		textProdName.setEditable(false);
		textProdName.setText(name);
		textProdName.setBounds(124, 36, 137, 22);
		contentPanel.add(textProdName);

		TextField textProdPrice = new TextField();
		textProdPrice.setEditable(false);
		textProdPrice.setText(price);
		textProdPrice.setBounds(124, 78, 137, 22);
		contentPanel.add(textProdPrice);

		TextField textProdQuantity = new TextField();
		textProdQuantity.setEditable(false);
		textProdQuantity.setText(quantity);
		textProdQuantity.setBounds(124, 122, 137, 22);
		contentPanel.add(textProdQuantity);

		Choice choiceProdPromo = new Choice();
		choiceProdPromo.setEnabled(false);
		choiceProdPromo.setBounds(124, 166, 137, 20);
		if (promo.equals("None")) {
			choiceProdPromo.add("None");
			choiceProdPromo.add("3 for 2");
			choiceProdPromo.add("Buy 1 get 1 free");
			choiceProdPromo.add("Free delivery");
		} else if (promo.equals("3 for 2")) {
			choiceProdPromo.add("3 for 2");
			choiceProdPromo.add("None");
			choiceProdPromo.add("Buy 1 get 1 free");
			choiceProdPromo.add("Free delivery");
		} else if (promo.equals("Buy 1 get 1 free")) {
			choiceProdPromo.add("Buy 1 get 1 free");
			choiceProdPromo.add("None");
			choiceProdPromo.add("3 for 2");
			choiceProdPromo.add("Free delivery");
		} else if (promo.equals("Free delivery")) {
			choiceProdPromo.add("Free delivery");
			choiceProdPromo.add("None");
			choiceProdPromo.add("3 for 2");
			choiceProdPromo.add("Buy 1 get 1 free");
		}
		contentPanel.add(choiceProdPromo);

		JLabel lblProductPromo = new JLabel("Product Promo:");
		lblProductPromo.setBounds(10, 166, 108, 20);
		contentPanel.add(lblProductPromo);

		JLabel lblAddProduct = new JLabel("Found Product");
		lblAddProduct.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAddProduct.setBounds(88, 11, 114, 14);
		contentPanel.add(lblAddProduct);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton editProduct = new JButton("Edit");
				buttonPane.add(editProduct);
				getRootPane().setDefaultButton(editProduct);

				editProduct.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						textProdQuantity.setEditable(true);
						textProdPrice.setEditable(true);
						choiceProdPromo.setEnabled(true);
					}
				});
			}

			JButton btnDelete = new JButton("Delete");
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null,
							"Are you sure you want to delete this product?",
							"Warning", dialogButton);
					if (dialogResult == 0) {
						String result = appLayer.deleteProduct(name);
						System.out.println(result);
						dispose();
					}
				}
			});
			buttonPane.add(btnDelete);
			{
				JButton cancelAddProductButton = new JButton("Done");
				cancelAddProductButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							String name = foundProduct.getName();
							int price = Integer.parseInt(textProdPrice
									.getText());
							int quantity = Integer.parseInt(textProdQuantity
									.getText());
							String promo = choiceProdPromo.getSelectedItem();
							String result = appLayer.editProduct(name, price,
									quantity, promo);
							System.out.println(result);
							dispose();
						} catch (NumberFormatException ne) {
							JOptionPane
									.showMessageDialog(
											contentPanel,
											"Please ensure price and quantity inputs are integers and no inputs are left blank.",
											"Warning",
											JOptionPane.WARNING_MESSAGE);
						}
					}
				});
				cancelAddProductButton.setActionCommand("Cancel");
				buttonPane.add(cancelAddProductButton);
			}
		}
	}
}
