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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import business.Concert;
import controllers.AbonatController;

@SuppressWarnings("serial")
public class AbonatFrame extends JFrame implements Observer {

	AbonatController aCTRL;
	private JPanel controlPanel;
	@SuppressWarnings("rawtypes")
	private JList concert_list;
	private JTextField txtLocuriDisp, txtLocuriDorite;
	private JLabel lblLocuriDisp, lblLocuriDorite;
	private JButton btnRezerva, btnClear;

	/**
	 *
	 * @param aCTRL
	 */
	public AbonatFrame(AbonatController aCTRL) {
		this.aCTRL = aCTRL;
		aCTRL.addObserver(this);
		init();
	}

	/**
	 *
	 */
	private void init() {
		setTitle("Abonat - " + aCTRL.getName());
		getContentPane().add(getJContentPanel());
		loadConcerte();
		pack();
		setVisible(true);
	}
	
	@SuppressWarnings("unchecked")
	void loadConcerte() {
		concert_list.setModel(aCTRL.getConcertListModel());
		if ((concert_list.getModel()).getSize() > 0)
			concert_list.setSelectedIndex(0);
	}

	void clear() {
		txtLocuriDorite.setText("");
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
			scrollpanel.setPreferredSize(new Dimension(220, 130));
			scrollpanel.setViewportView(getJListConcerte());
			p1.add(scrollpanel, JScrollPane.HORIZONTAL_SCROLLBAR);
			
			
			JPanel p2 = new JPanel(new GridLayout(4, 1));
			p2.setBorder(BorderFactory.createTitledBorder("Rezervare Locuri Concert"));

			JPanel p2_1 = new JPanel();
			p2_1.add(getJLabelAvailablePrQty());
			p2_1.add(getJTextAvailablePrQty());
			JPanel p2_2 = new JPanel();
			p2_2.add(getJLabelSelectedPrQty());
			p2_2.add(getJTextSelectedPrQty());
			JPanel p2_3 = new JPanel();
			p2_3.add(getJButtonRezerva());
			p2_3.add(getJButtonClear());

			p2.add(p2_1);
			p2.add(p2_2);
			p2.add(p2_3);
			
			JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, p1, p2);
			controlPanel.add(sp);
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
			concert_list.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					int index=concert_list.getSelectedIndex();
					if (index>-1){
						Concert p = ((AbonatFrame.this.aCTRL).getConcertListModel()).get(index);
						txtLocuriDisp.setText(p.getLocuriDisponibile() + "");
					}
				}
			});
		}
		return concert_list;
	}

	/**
	 * label creation
	 */
	public JLabel getJLabelAvailablePrQty() {
		if (lblLocuriDisp == null) {
			lblLocuriDisp = new JLabel("Locuri Disponibile la concert");
		}
		return lblLocuriDisp;
	}
	
	public JLabel getJLabelSelectedPrQty() {
		if (lblLocuriDorite == null) {
			lblLocuriDorite = new JLabel("Cate locuri doriti sa rezervati?");
		}
		return lblLocuriDorite;
	}

	/**
	 * text field creation
	 */
	public JTextField getJTextAvailablePrQty() {
		if (txtLocuriDisp == null) {
			txtLocuriDisp = new JTextField(10);
		}
		txtLocuriDisp.setEditable(false);
		return txtLocuriDisp;
	}
	
	public JTextField getJTextSelectedPrQty() {
		if (txtLocuriDorite == null) {
			txtLocuriDorite = new JTextField(10);
		}
		return txtLocuriDorite;
	}

	/**
	 * Button creation
	 */
	public JButton getJButtonRezerva() {
		if (btnRezerva == null) {
			btnRezerva = new JButton("Rezerva");
			btnRezerva.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (concert_list.getSelectedIndex() < 0) {
						JOptionPane.showMessageDialog(AbonatFrame.this, "Selecteaza un concert", "Eroare", JOptionPane.ERROR_MESSAGE);
						return;
					}

					try {
						int qty = Integer.parseInt(txtLocuriDorite.getText());
						if (qty <= 0)
							throw new Exception();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(AbonatFrame.this, "Introdu un numar valid de locuri dorite", "Eroare", JOptionPane.ERROR_MESSAGE);
						txtLocuriDorite.setText("");
						return;
					}

					int index=concert_list.getSelectedIndex();
					Concert p = ((AbonatFrame.this.aCTRL).getConcertListModel()).get(index);
					String concert = p.getDenumire();
					try {
						aCTRL.rezervaLocuri(concert, Integer.parseInt(txtLocuriDorite.getText()));
						JOptionPane.showMessageDialog(AbonatFrame.this, "Ati rezervat " + txtLocuriDorite.getText() + " bilete pentru " + concert, "Succes", JOptionPane.INFORMATION_MESSAGE);
						txtLocuriDorite.setText("");
						loadConcerte();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(AbonatFrame.this, e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		return btnRezerva;
	}

	public JButton getJButtonClear() {
		if (btnClear == null) {
			btnClear = new JButton("Clear");
			btnClear.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					clear();
				}
			});
		}
		return btnClear;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		aCTRL.updateConcertListModel();
		loadConcerte();
	}
}