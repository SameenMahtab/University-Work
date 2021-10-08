package ca.mcgill.ecse223.resto.view;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MainPage extends JFrame {

	private static final long serialVersionUID = -6671167773705738484L;

	private JButton ctp, mp, mtp, omp, rsp, rtp, rtp2, utp;
	private JPanel tablesLayout;
	private JLabel titleLabel;

	public MainPage() {
		initComponents();
	}

	private void initComponents() {

		TablesLayoutPage tlp = new TablesLayoutPage();
		tablesLayout = tlp.getTableLayoutPanel();

		// elements
		ctp = new JButton();
		mp = new JButton();
		mtp = new JButton();
		omp = new JButton();
		rsp = new JButton();
		rtp = new JButton();
		rtp2 = new JButton();
		utp = new JButton();

		titleLabel = new JLabel();

		titleLabel.setText("RESTO APP");

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Main Page");

		// Set text
		ctp.setText("Create Table Page");
		mp.setText("Menu Page");
		mtp.setText("Move Table Page");
		omp.setText("Orders Main Page");
		rsp.setText("Restaurant Status Page");
		rtp.setText("Remove Table Page");
		rtp2.setText("Reserve Table Page");
		utp.setText("Update Table Page");

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		// layout.setHorizontalGroup(layout.createSequentialGroup()
		// .addGroup(layout.createParallelGroup()
		// .addComponent(ctp)
		// .addComponent(mp))
		// .addGroup(layout.createParallelGroup()
		// .addComponent(mtp)
		// .addComponent(omp))
		// .addGroup(layout.createParallelGroup()
		// .addComponent(rsp)
		// .addComponent(rtp))
		// .addGroup(layout.createParallelGroup()
		// .addComponent(rtp2)
		// .addComponent(utp))
		// );
		//
		// layout.setVerticalGroup(layout.createSequentialGroup()
		// .addGroup(layout.createParallelGroup()
		// .addComponent(ctp)
		// .addComponent(mtp)
		// .addComponent(rsp)
		// .addComponent(rtp2))
		// .addGroup(layout.createParallelGroup()
		// .addComponent(mp)
		// .addComponent(omp)
		// .addComponent(rtp)
		// .addComponent(utp))
		// );

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(rtp2).addComponent(ctp))
				.addGroup(layout.createParallelGroup().addComponent(rsp).addComponent(rtp))
				.addGroup(layout.createParallelGroup().addComponent(titleLabel).addComponent(tablesLayout))
				.addGroup(layout.createParallelGroup().addComponent(mp).addComponent(utp))
				.addGroup(layout.createParallelGroup().addComponent(omp).addComponent(mtp)));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(rtp2).addComponent(rsp).addComponent(titleLabel)
						.addComponent(mp).addComponent(omp))
				.addGroup(layout.createParallelGroup().addComponent(tablesLayout)).addGroup(layout.createParallelGroup()
						.addComponent(ctp).addComponent(rtp).addComponent(utp).addComponent(mtp)));

		ctp.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadPage("ctp");
			}
		});

		mp.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadPage("mp");
			}
		});

		mtp.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadPage("mtp");
			}
		});

		omp.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadPage("omp");
			}
		});

		rsp.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadPage("rsp");
			}
		});

		rtp.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadPage("rtp");
			}
		});

		rtp2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadPage("rtp2");
			}
		});

		utp.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadPage("utp");
			}
		});
	}

	public void loadPage(String pageName) {
		switch (pageName) {
		case "ctp": {
			CreateTablePage ctp = new CreateTablePage();
			ctp.setSize(500, 200);
			ctp.setVisible(true);
			break;
		}
		case "mp": {
			MenuPage mp = new MenuPage();
			mp.setSize(500, 500);
			mp.setVisible(true);
			break;
		}
		case "mtp": {
			MoveTablePage mtp = new MoveTablePage();
			mtp.setSize(500, 200);
			mtp.setVisible(true);
			break;
		}
		case "omp": {
			OrdersMainPage omp = new OrdersMainPage();
			omp.setSize(375, 200);
			omp.setVisible(true);
			break;
		}
		case "rsp": {
			RestaurantStatusPage rsp = new RestaurantStatusPage();
			rsp.setSize(500, 500);
			rsp.setVisible(true);
			break;
		}
		case "rtp": {
			RemoveTablePage rtp = new RemoveTablePage();
			rtp.setSize(300, 150);
			rtp.setVisible(true);
			break;
		}
		case "rtp2": {
			ReserveTablePage rtp2 = new ReserveTablePage();
			rtp2.setSize(1100, 500);
			rtp2.setVisible(true);
			break;
		}
		case "utp": {
			UpdateTablePage utp = new UpdateTablePage();
			utp.setSize(300, 200);
			utp.setVisible(true);
			break;
		}
		case "main": {
			MainPage main = new MainPage();
			main.setSize(1370, 732);
			main.setVisible(true);
		}
		default: {

		}
		}
	}
}
