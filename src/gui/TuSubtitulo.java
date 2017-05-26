package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import java.awt.Toolkit;
import java.awt.Font;

public class TuSubtitulo {

	private JFrame frame;
	private JTextField txtstringsdownload;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TuSubtitulo window = new TuSubtitulo();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TuSubtitulo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setFont(new Font("Droid Sans", Font.PLAIN, 19));
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("/home/carlos/Repositorios/Tusubtitulo.com-Downloader-Unofficial-/res/icon.png"));
		frame.setBounds(100, 100, 500, 370);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblNewLabel = new JLabel("Serie");
		
		JButton btnNewButton = new JButton("Buscar");
		
		txtstringsdownload = new JTextField();
		txtstringsdownload.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Temporada");
		lblNewLabel_1.setToolTipText("");
		
		JSpinner spinner = new JSpinner();
		
		JSpinner spinner_1 = new JSpinner();
		
		JLabel lblNewLabel_2 = new JLabel("Episodio");
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Descargar todos");
		
		JButton btnNewButton_1 = new JButton("Descargas");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(rdbtnNewRadioButton))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(45)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel)
									.addGap(27)
									.addComponent(txtstringsdownload, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnNewButton))
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel_1)
										.addComponent(lblNewLabel_2))
									.addPreferredGap(ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(spinner_1)
										.addComponent(spinner, GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))))))
					.addGap(32))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(201, Short.MAX_VALUE)
					.addComponent(btnNewButton_1)
					.addGap(187))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(53)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(btnNewButton)
						.addComponent(txtstringsdownload, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnNewRadioButton)
					.addGap(18)
					.addComponent(btnNewButton_1)
					.addContainerGap(115, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
