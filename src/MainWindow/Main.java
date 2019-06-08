package MainWindow;

import Buttons.ClientBtn;
import Buttons.LeaveBtn;
import Buttons.SendMsgBtn;
import Buttons.ServerBtn;
import Chat.Server;
import field.Field;
import field.FieldListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static ArrayList<Field> fields = new ArrayList<Field>();
    public static ArrayList<FieldListener> fieldListeners = new ArrayList<FieldListener>();
    public static SendMsgBtn sendMsgBtn = new SendMsgBtn();
    public static ServerBtn serverBtn = new ServerBtn(sendMsgBtn);
    public static ClientBtn clientBtn = new ClientBtn(sendMsgBtn);
    public static LeaveBtn leaveBtn = new LeaveBtn();

    public static JLabel ipLabel = new JLabel();
    public static JTextField ipField = new JTextField(10);
    public static JLabel portLabel = new JLabel();
    public static JTextField portField = new JTextField(10);
    public static JTextField message = new JTextField(40);
    public static JTextArea allMsgs = new JTextArea();

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("Tic-Tac-Toe");
        mainFrame.setVisible(true);
        mainFrame.setMinimumSize(new Dimension(1060, 600));
        //mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);
        Container container = mainFrame.getContentPane();
        container.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        btnPanel.add(serverBtn.getButton());
        btnPanel.add(clientBtn.getButton());
        btnPanel.add(leaveBtn.getButton());
        ipLabel.setText(" IP address: ");
        btnPanel.add(ipLabel);
        btnPanel.add(ipField);
        portLabel.setText(" PORT: ");
        btnPanel.add(portLabel);
        btnPanel.add(portField);

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 3, 5, 5));
        for (int i = 0; i < 9; i++){
            fields.add(new Field());
            fieldListeners.add(new FieldListener(fields.get(i)));
            fields.get(i).addMouseListener(fieldListeners.get(i));
            gamePanel.add(fields.get(i));
        }


        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());


        JPanel mesSend = new JPanel();
        mesSend.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        mesSend.add(message);

        mesSend.add(sendMsgBtn.getButton());


        allMsgs.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(allMsgs);
        scrollPane.setBounds(10,60,780,500);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        Border border = BorderFactory.createLineBorder(Color.BLACK);
        allMsgs.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        messagePanel.add(scrollPane, BorderLayout.CENTER);
        messagePanel.add(mesSend, BorderLayout.SOUTH);


        leftPanel.add(btnPanel, BorderLayout.NORTH);
        leftPanel.add(gamePanel, BorderLayout.CENTER);

        container.add(leftPanel, BorderLayout.LINE_START);
        container.add(messagePanel,  BorderLayout.CENTER);

    }
}
