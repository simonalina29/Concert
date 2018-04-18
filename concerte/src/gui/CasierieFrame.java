package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import business.Casierie;
import business.AbonatException;
import business.ConcertException;
import controllers.CasierieController;
import controllers.AbonatController;

@SuppressWarnings("serial")
public class CasierieFrame extends JFrame implements Observer {


	CasierieController ctrl;
	private JPanel controlPanel;
	@SuppressWarnings("rawtypes")
	private JList concert_list, abonat_list;
	private JTextField txtCDescriere, txtCData, txtCLocuri, txtANume,txtEmail;
	private JLabel lblCDescriere, lblCData, lblCLocuri, lblANume,lEmail;
	private JButton bAddC, bAddA, bClearC, bClearA;
	Casierie cas;

	/**
	 * Constructor
	 */
	public CasierieFrame(Casierie agency, CasierieController agCTRL) {
		this.cas = agency;
		this.ctrl = agCTRL;
		agCTRL.addObserver(this);
		init();
	}

	/**
	 * Initiate the GUI
	 */
	private void init() {
		setTitle("Casierie");
		setLocation(600, 100);
		getContentPane().add(getJContentPanel());
		loadConcerte();
		loadAbonati();
		pack();
		setVisible(true);
	}

	@SuppressWarnings("unchecked")
	void loadConcerte() {
		concert_list.setModel(ctrl.getConcertListModel());
		if ((concert_list.getModel()).getSize() > 0)
			concert_list.setSelectedIndex(0);
	}

	@SuppressWarnings("unchecked")
	void loadAbonati() {
		abonat_list.setModel(ctrl.getAbonatListModel());
		if ((abonat_list.getModel()).getSize() > 0)
			abonat_list.setSelectedIndex(0);
	}

	void clearAllFields() {
		txtCDescriere.setText("");
		txtCData.setText("");
		txtCLocuri.setText("");
	}

	void clearAgFields() {
		txtANume.setText("");
	}

	/**
	 * Creates the panel
	 */
	private JPanel getJContentPanel() {
		if (controlPanel == null) {

			controlPanel = new JPanel();
			controlPanel.setLayout(new GridLayout(1, 1));

			JPanel p1 = new JPanel();
			p1.setBorder(BorderFactory.createTitledBorder("Concerte"));

			JScrollPane scrollpanel = new JScrollPane();
			scrollpanel.setPreferredSize(new Dimension(250, 130));

			scrollpanel.setViewportView(getJListConcerte());
			p1.add(scrollpanel, JScrollPane.HORIZONTAL_SCROLLBAR);

			JPanel p2 = new JPanel();
			p2.setBorder(BorderFactory.createTitledBorder("Abonati"));

			JScrollPane scrollpanel2 = new JScrollPane();
			scrollpanel2.setPreferredSize(new Dimension(220, 130));
			scrollpanel2.setViewportView(getJListAbonati());
			p2.add(scrollpanel2, JScrollPane.HORIZONTAL_SCROLLBAR);

			JPanel p3 = new JPanel(new GridLayout(4, 1));
			p3.setBorder(BorderFactory.createTitledBorder("Adauga Concert nou"));

			JPanel p3_1 = new JPanel();
			p3_1.add(getJLabelCNume());
			p3_1.add(getJTextCNume());
			JPanel p3_2 = new JPanel();
			p3_2.add(getJLabelCData());
			p3_2.add(getJTextCData());
			JPanel p3_3 = new JPanel();
			p3_3.add(getJLabelCLocuri());
			p3_3.add(getJTextCLocuri());
			JPanel p3_4 = new JPanel();
			p3_4.add(getJButtonAddConcert());
			p3_4.add(getJButtonClearPr());

			p3.add(p3_1);
			p3.add(p3_2);
			p3.add(p3_3);
			p3.add(p3_4);

			JPanel p4 = new JPanel(new GridLayout(3, 1));
			p4.setBorder(BorderFactory.createTitledBorder("Adauga Abonat nou"));

			JPanel p4_1 = new JPanel();
			p4_1.add(getJLabelANume());
			p4_1.add(getJTextANume());
			JPanel p4_3 = new JPanel();
			p4_3.add(getJLabelCEmail());
			p4_3.add(getJTextEmail());
			JPanel p4_2 = new JPanel();
			p4_2.add(getJButtonAddAbonat());
			p4_2.add(getJButtonClearAg());
			p4.add(p4_1);
			p4.add(p4_3);
			p4.add(p4_2);

			
			JSplitPane sp1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, p1, p2);
			JSplitPane sp2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, p3, p4);
			controlPanel.add(sp1);
			controlPanel.add(sp2);
			
		}
		return controlPanel;
	}

	/**
	 * Concert list creation
	 */
	@SuppressWarnings({ "rawtypes" })
	private JList getJListConcerte() {
		if (concert_list == null) {
			concert_list = new JList(); // ctrl.getListModel());
			concert_list.setSelectedIndex(0);
			concert_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			concert_list.setVisibleRowCount(15);
		}
		return concert_list;
	}

	/**
	 * Abonat list creation
	 */
	@SuppressWarnings({ "rawtypes" })
	private JList getJListAbonati() {
		if (abonat_list == null) {
			abonat_list = new JList(); // ctrl.getListModel());
			abonat_list.setSelectedIndex(0);
			abonat_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			abonat_list.setVisibleRowCount(15);
		}
		return abonat_list;
	}

	/**
	 * Label creation
	 */
	public JLabel getJLabelCNume() {
		if (lblCDescriere == null) {
			lblCDescriere = new JLabel("Nume Concert");
		}
		return lblCDescriere;
	}
	public JLabel getJLabelCEmail(){
		if (lEmail==null){
			lEmail=new JLabel("Email Abonat");
		}
		return lEmail;
	}
	public JLabel getJLabelCData() {
		if (lblCData == null) {
			lblCData = new JLabel("Data Concert");
		}
		return lblCData;
	}

	public JLabel getJLabelCLocuri() {
		if (lblCLocuri == null) {
			lblCLocuri = new JLabel("Locuri disponibile concert");
		}
		return lblCLocuri;
	}

	public JLabel getJLabelANume() {
		if (lblANume == null) {
			lblANume = new JLabel("Nume Abonat");
		}
		return lblANume;
	}

	/**
	 * Text field creation
	 */
	public JTextField getJTextCNume() {
		if (txtCDescriere == null) {
			txtCDescriere = new JTextField(10);
		}
		return txtCDescriere;
	}

	public JTextField getJTextCData() {
		if (txtCData == null) {
			txtCData = new JTextField(10);
		}
		return txtCData;
	}

	public JTextField getJTextCLocuri() {
		if (txtCLocuri == null) {
			txtCLocuri = new JTextField(10);
		}
		return txtCLocuri;
	}

	public JTextField getJTextANume() {
		if (txtANume == null) {
			txtANume = new JTextField(10);
		}
		return txtANume;
	}
	public JTextField getJTextEmail(){
		if (txtEmail==null){
			txtEmail=new JTextField(10);
		}
		return txtEmail;
	}

	/**
	 * Button creation
	 */
	public JButton getJButtonAddConcert() {
		if (bAddC == null) {
			bAddC = new JButton("Adauga Concert");
			bAddC.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (txtCDescriere.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(CasierieFrame.this, "Introduceti numele concertului", "Eroare",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					try {
						String[] parts = txtCData.getText().trim().split("/");
						if (parts.length != 3)
							throw new Exception();
						Integer.parseInt(parts[0]);
						Integer.parseInt(parts[1]);
						Integer.parseInt(parts[2]);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(CasierieFrame.this, " Format: zz/ll/aaaa", "Eroare",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					try {
						int nrLocuri = Integer.parseInt(txtCLocuri.getText());
						if (nrLocuri <= 0)
							throw new Exception();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(CasierieFrame.this, "Acesta nu este un numar valid de locuri", "Eroare",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					try {
						ctrl.addConcert(txtCDescriere.getText(), txtCData.getText(), txtCLocuri.getText(),
								txtCLocuri.getText());
						clearAllFields();
					} catch (ConcertException e) {
						JOptionPane.showMessageDialog(CasierieFrame.this, e.getMessage(), "Eroare",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		return bAddC;
	}

	public JButton getJButtonAddAbonat() {
		if (bAddA == null) {
			bAddA = new JButton("Adauga Abonat");
			bAddA.addActionListener(new ActionListener() {
				@SuppressWarnings("unused")
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (txtANume.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(CasierieFrame.this, "Introduceti numele abonatului", "Eroare",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					try {
						
						ctrl.addAbonat(txtANume.getText(),txtEmail.getText());
						AbonatController aCTRL = new AbonatController(cas, txtANume.getText()+txtEmail.getText());
						clearAgFields();
						AbonatFrame abonatWindow = new AbonatFrame(aCTRL);						
					} catch (AbonatException e) {
						JOptionPane.showMessageDialog(CasierieFrame.this, e.getMessage(), "Eroare",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		return bAddA;
	}

	public JButton getJButtonClearPr() {
		if (bClearC == null) {
			bClearC = new JButton("Clear");
			bClearC.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					clearAllFields();
				}
			});
		}
		return bClearC;
	}

	public JButton getJButtonClearAg() {
		if (bClearA == null) {
			bClearA = new JButton("Clear");
			bClearA.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					clearAgFields();
				}
			});
		}
		return bClearA;
	}

	@Override
	public void update(Observable arg0, Object arg1) {		
		ctrl.updateConcertListModel();
		loadConcerte();
	}
}