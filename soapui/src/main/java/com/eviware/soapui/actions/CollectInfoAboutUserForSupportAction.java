package com.eviware.soapui.actions;

import com.eviware.soapui.analytics.Analytics;
import com.eviware.soapui.support.StringUtils;
import com.eviware.soapui.support.UISupport;
import com.eviware.soapui.support.components.JFriendlyTextField;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CollectInfoAboutUserForSupportAction {
    private static final String NAME_HINT = "Enter your name *";
    private static final String EMAIL_HINT = "Enter e-mail *";
    private static final String DIALOG_CAPTION = "Stay Tuned!";
    private static final String DIALOG_MAIN_TEXT = "??? What should be here ???";
    private static final String DIALOG_DESCRIPTION = "Provide your e-mail address to stay up-to-date on product updates, exclusive offers and materials to get the most out of your SoapUI download.";
    private static final String OK_BTN_CAPTION = "Yes, I want to get additional materials";
    private static final String SKIP_BTN_CAPTION = "Skip";

    public CollectInfoAboutUserForSupportAction() {
    }

    public void show() {
        CollectUserInfoDialog cui = new CollectUserInfoDialog();
        cui.setVisible(true);
    }

    private class CollectUserInfoDialog extends JDialog {
        private JLabel title;
        private JLabel description;
        private JFriendlyTextField textFieldName;
        private JFriendlyTextField textFieldEmail;
        private static final String VALID_EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        private Pattern validEmailRegex;

        private void setBackgroundColor(JPanel curPanel) {
            curPanel.setOpaque(true);
            curPanel.setBackground(Color.WHITE);
        }

        private void setBackgroundColor(JLabel curLabel) {
            curLabel.setOpaque(true);
            curLabel.setBackground(Color.WHITE);
        }

        public CollectUserInfoDialog() {
            super(UISupport.getMainFrame(), DIALOG_CAPTION, true);
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            setModal(true);
            setSize(430, 265);
            setBackground(Color.WHITE);
            JPanel jBasePanel = new JPanel(new BorderLayout(5, 5));
            setBackgroundColor(jBasePanel);
            this.add(jBasePanel);

            jBasePanel.add(buildCaptionPanel(DIALOG_MAIN_TEXT, DIALOG_DESCRIPTION), BorderLayout.NORTH);
            jBasePanel.add(buildControlsPanel());

            validEmailRegex = Pattern.compile(VALID_EMAIL_PATTERN);
        }

        private JPanel buildCaptionPanel(String titleStr, String descriptionStr) {
            JPanel jRoot = new JPanel(new BorderLayout());
            jRoot.setBorder(new EmptyBorder(10, 30, 0, 25));
            setBackgroundColor(jRoot);
            title = new JLabel();
            setBackgroundColor(title);
            title.setText("<html><div style=\"font-size: 11px\"><b>" + titleStr + "</b></div></html>");
            title.setOpaque(true);
            title.setBackground(Color.WHITE);
            description = new JLabel();
            setBackgroundColor(description);
            description.setText("<html><div style=\"font-size: 9px\">" + descriptionStr + "</div></html>");
            description.setBorder(new EmptyBorder(5, 0, 0, 0));

            jRoot.add(title, BorderLayout.NORTH);
            jRoot.add(description);

            return jRoot;
        }

        private JPanel buildControlsPanel() {
            JPanel jbase = new JPanel(new BorderLayout());

            JPanel jLeftPanel = new JPanel();
            jLeftPanel.setLayout(new BorderLayout());
            jLeftPanel.setBorder(new EmptyBorder(0, 0, 25, 0));
            JLabel arrowIcon = new JLabel(UISupport.createImageIcon("/big_arrow.png"));
            jLeftPanel.add(arrowIcon, BorderLayout.SOUTH);
            setBackgroundColor(jLeftPanel);

            JPanel jControlsPanel = new JPanel(new BorderLayout());
            jControlsPanel.setBorder(new EmptyBorder(5, 2, 10, 35));
            setBackgroundColor(jControlsPanel);
            jControlsPanel.add(buildButtonsPanel(), BorderLayout.SOUTH);
            jControlsPanel.add(buildUserInfoPanel());

            jbase.add(jLeftPanel, BorderLayout.WEST);
            jbase.add(jControlsPanel);

            return jbase;
        }

        private JPanel buildUserInfoPanel() {
            textFieldName = new JFriendlyTextField(NAME_HINT);
            textFieldName.setPreferredSize(new Dimension(300, 24));

            textFieldEmail = new JFriendlyTextField(EMAIL_HINT);
            textFieldEmail.setPreferredSize(new Dimension(300, 24));
            JPanel jHelpEmail = new JPanel(new BorderLayout());
            setBackgroundColor(jHelpEmail);
            jHelpEmail.setBorder(new EmptyBorder(8, 0, 0, 0));
            jHelpEmail.add(textFieldEmail, BorderLayout.NORTH);

            JPanel userInfoContent = new JPanel(new BorderLayout());
            setBackgroundColor(userInfoContent);
            userInfoContent.add(textFieldName, BorderLayout.NORTH);
            userInfoContent.add(jHelpEmail);

            return userInfoContent;
        }

        private JPanel buildButtonsPanel() {
            JButton jOkBtn = new JButton(OK_BTN_CAPTION);
            jOkBtn.setBorder(new LineBorder(new Color(200, 200, 200), 1));
            jOkBtn.setBackground(new Color(157, 200, 130));
            jOkBtn.setForeground(Color.WHITE);
            jOkBtn.setPreferredSize(new Dimension(300, 24));
            jOkBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (handleOk()) {
                        setVisible(false);
                    }
                }
            });

            JPanel jOkPanel = new JPanel(new BorderLayout());
            jOkPanel.setBorder(new EmptyBorder(0, 0, 0, 10));
            setBackgroundColor(jOkPanel);
            jOkPanel.add(jOkBtn);

            JButton jSkip = new JButton(SKIP_BTN_CAPTION);
            jSkip.setBorder(new LineBorder(new Color(170, 170, 170), 1));
            jSkip.setForeground(new Color(170, 170, 170));
            jSkip.setPreferredSize(new Dimension(60, 20));
            jSkip.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                }
            });

            JPanel buttonsContent = new JPanel(new BorderLayout());
            buttonsContent.setBorder(new EmptyBorder(20, 0, 20, 0));
            setBackgroundColor(buttonsContent);

            buttonsContent.add(jSkip, BorderLayout.EAST);
            buttonsContent.add(jOkPanel);

            return buttonsContent;
        }

        @Override
        public void setVisible(boolean b) {
            UISupport.centerDialog(this);
            super.setVisible(b);
        }

        private String getUserName() {
            String name = textFieldName.getText();
            name = name.replace(NAME_HINT, "");
            return name;
        }

        private String getUserEMail() {
            String email = textFieldEmail.getText();
            email = email.replace(EMAIL_HINT, "");
            return email;
        }

        protected boolean handleOk() {
            if (!verifyFormValues()) {
                return false;
            }
            Analytics.trackOSUser(getUserName(), "", getUserEMail());
            return true;
        }

        private boolean verifyFormValues() {
            List<String> fieldErrors = new ArrayList<String>();
            if (StringUtils.isNullOrEmpty(getUserName())) {
                fieldErrors.add("your name");
            }
            if (!isValidEmailAddress(getUserEMail())) {
                fieldErrors.add("a valid email address");
            }
            if (fieldErrors.isEmpty()) {
                return true;
            } else {
                StringBuilder buf = new StringBuilder("You must enter ");
                int numberOfErrors = fieldErrors.size();
                for (int i = 0; i < numberOfErrors; i++) {
                    if (i > 0) {
                        buf.append(i < numberOfErrors - 1 ? ", " : " and ");
                    }
                    buf.append(fieldErrors.get(i));
                }
                buf.append(".");
                UISupport.showErrorMessage(buf.toString());
                return false;
            }
        }

        private boolean isValidEmailAddress(String email) {
            return StringUtils.hasContent(email) && validEmailRegex.matcher(email).matches();
        }
    }
}
