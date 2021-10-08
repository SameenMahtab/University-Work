package ca.mcgill.ecse223.resto.view;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class OrdersMainPage extends JFrame {

	private static final long serialVersionUID = -6671167773705738484L;

	private JButton omip, coip, vop, cop, ibp, sop, eop;

	public OrdersMainPage() {
		initComponents();
	}

	private void initComponents() {
		// elements
		omip = new JButton();
		coip = new JButton();
		vop = new JButton();
		cop = new JButton();
		ibp = new JButton();
		sop = new JButton();
		eop = new JButton();

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("Orders Main Page");

		// Set text
		omip.setText("Order Menu Item Page");
		coip.setText("Cancel Order Item Page");
		vop.setText("View Order Page");
		cop.setText("Cancel Order Page");
		ibp.setText("Issue Bill Page");
		sop.setText("Start Order");
		eop.setText("End Order");

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(omip)
						.addComponent(coip)
						.addComponent(ibp)
						.addComponent(sop))
				.addGroup(layout.createParallelGroup()
						.addComponent(vop)
						.addComponent(cop)
						.addComponent(eop)));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(omip)
						.addComponent(vop))
				.addGroup(layout.createParallelGroup()
						.addComponent(coip)
						.addComponent(cop))
				.addGroup(layout.createParallelGroup()
						.addComponent(ibp)
						.addComponent(eop))
				.addComponent(sop));

		omip.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadPage("omip");
			}
		});
		
		coip.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadPage("coip");
			}
		});
		
		vop.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadPage("vop");
			}
		});
		
		cop.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadPage("cop");
			}
		});
		
		ibp.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadPage("ibp");
			}
		});
		
		sop.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadPage("sop");
			}
		});
		
		eop.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadPage("eop");
			}
		});
	}
	
	public void loadPage(String pageName) {
		switch (pageName) {
		case "omip": {
			OrderMenuItemPage omip = new OrderMenuItemPage();
			omip.setSize(750, 325);
			omip.setVisible(true);
			break;
		}
		case "coip": {
			CancelOrderItemPage coip = new CancelOrderItemPage();
			coip.setSize(650, 300);
			coip.setVisible(true);
			break;
		}
		case "vop": { // chop is a step between this and vop
			ChooseOrderPage chop = new ChooseOrderPage();
			chop.setSize(350, 200);
			chop.setVisible(true);
			break;
		}
		case "cop": {
			CancelOrderPage cop = new CancelOrderPage();
			cop.setSize(500, 200);
			cop.setVisible(true);
			break;
		}
		case "ibp": {
			IssueBillPage ibp = new IssueBillPage();
			ibp.setSize(300, 150);
			ibp.setVisible(true);
			break;
		}
		case "sop": {
			StartOrderPage sop = new StartOrderPage();
			sop.setSize(500, 200);
			sop.setVisible(true);
			break;
		}
		case "eop": {
			EndOrderPage eop = new EndOrderPage();
			eop.setSize(500, 300);
			eop.setVisible(true);
			break;
		}
		default: {

		}
		}
	}
}
