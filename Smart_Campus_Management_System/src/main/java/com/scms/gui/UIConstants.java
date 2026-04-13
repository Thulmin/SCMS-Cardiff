// Declare that this file belongs to the com.scms.gui package.
package com.scms.gui;

// Import javax.swing.* so it can be used in this file.
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

// Declare the UI constants class.
public class UIConstants {
    // Color Palette
    // Create and store the primary.
    public static final Color PRIMARY = new Color(25, 42, 86);
    // Create and store the primary LIGHT.
    public static final Color PRIMARY_LIGHT = new Color(40, 65, 120);
    // Create and store the accent.
    public static final Color ACCENT = new Color(52, 152, 219);
    // Create and store the accent HOVER.
    public static final Color ACCENT_HOVER = new Color(41, 128, 185);
    // Create and store the success.
    public static final Color SUCCESS = new Color(39, 174, 96);
    // Create and store the warning.
    public static final Color WARNING = new Color(243, 156, 18);
    // Create and store the danger.
    public static final Color DANGER = new Color(231, 76, 60);
    // Create and store the BG MAIN.
    public static final Color BG_MAIN = new Color(236, 240, 241);
    // Calculate and store the BG CARD.
    public static final Color BG_CARD = Color.WHITE;
    // Create and store the TEXT primary.
    public static final Color TEXT_PRIMARY = new Color(44, 62, 80);
    // Create and store the TEXT secondary.
    public static final Color TEXT_SECONDARY = new Color(127, 140, 141);
    // Calculate and store the sidebar BG.
    public static final Color SIDEBAR_BG = PRIMARY;
    // Calculate and store the sidebar HOVER.
    public static final Color SIDEBAR_HOVER = PRIMARY_LIGHT;
    // Create and store the sidebar TEXT.
    public static final Color SIDEBAR_TEXT = new Color(200, 210, 230);
    // Calculate and store the sidebar active.
    public static final Color SIDEBAR_ACTIVE = ACCENT;
    // Create and store the border COLOR.
    public static final Color BORDER_COLOR = new Color(220, 220, 220);

    // Fonts
    // Create and store the FONT TITLE.
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    // Create and store the FONT subtitle.
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 18);
    // Create and store the FONT heading.
    public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD, 14);
    // Create and store the FONT BODY.
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 13);
    // Create and store the FONT SMALL.
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 11);
    // Create and store the FONT sidebar.
    public static final Font FONT_SIDEBAR = new Font("Segoe UI", Font.PLAIN, 14);
    // Create and store the FONT button.
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 13);

    // Dimensions
    // Store the sidebar WIDTH value.
    public static final int SIDEBAR_WIDTH = 240;
    // Create and store the button SIZE.
    public static final Dimension BUTTON_SIZE = new Dimension(140, 38);

    // Define the create styled button method.
    public static JButton createStyledButton(String text, Color bgColor) {
        // Create and store the button.
        JButton button = new JButton(text);
        // Set font on button.
        button.setFont(FONT_BUTTON);
        // Set background on button.
        button.setBackground(bgColor);
        // Set foreground on button.
        button.setForeground(Color.WHITE);
        // Set focus painted on button.
        button.setFocusPainted(false);
        // Set border painted on button.
        button.setBorderPainted(false);
        // Set opaque on button.
        button.setOpaque(true);
        // Set cursor on button.
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Set preferred size on button.
        button.setPreferredSize(BUTTON_SIZE);
        // Add mouse listener on button.
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            // Calculate and store the original.
            Color original = bgColor;
            // Define the mouse entered method.
            public void mouseEntered(java.awt.event.MouseEvent e) {
                // Set background on button.
                button.setBackground(original.darker());
            }
            // Define the mouse exited method.
            public void mouseExited(java.awt.event.MouseEvent e) {
                // Set background on button.
                button.setBackground(original);
            }
        });
        // Return button to the caller.
        return button;
    }

    // Define the create card method.
    public static JPanel createCard(String title) {
        // Create and store the card.
        JPanel card = new JPanel(new BorderLayout(10, 10));
        // Set background on card.
        card.setBackground(BG_CARD);
        // Set border on card.
        card.setBorder(BorderFactory.createCompoundBorder(
            // Provide the next argument for set border.
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            // Provide the next argument for set border.
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        // Provide the remaining arguments needed to finish set border.
        ));
        // Check whether title is not null and not title.isEmpty().
        if (title != null && !title.isEmpty()) {
            // Create and store the title label.
            JLabel titleLabel = new JLabel(title);
            // Set font on title label.
            titleLabel.setFont(FONT_HEADING);
            // Set foreground on title label.
            titleLabel.setForeground(TEXT_PRIMARY);
            // Add on card.
            card.add(titleLabel, BorderLayout.NORTH);
        }
        // Return card to the caller.
        return card;
    }

    // Define the create stat card method.
    public static JPanel createStatCard(String label, String value, Color accentColor) {
        // Create and store the card.
        JPanel card = new JPanel();
        // Set layout on card.
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        // Set background on card.
        card.setBackground(BG_CARD);
        // Set border on card.
        card.setBorder(BorderFactory.createCompoundBorder(
            // Provide the next argument for set border.
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            // Provide the next argument for set border.
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        // Provide the remaining arguments needed to finish set border.
        ));

        // Create and store the color bar.
        JPanel colorBar = new JPanel();
        // Set background on color bar.
        colorBar.setBackground(accentColor);
        // Set maximum size on color bar.
        colorBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 4));
        // Set preferred size on color bar.
        colorBar.setPreferredSize(new Dimension(0, 4));
        // Add on card.
        card.add(colorBar);
        // Add on card.
        card.add(Box.createVerticalStrut(10));

        // Create and store the value label.
        JLabel valueLabel = new JLabel(value);
        // Set font on value label.
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        // Set foreground on value label.
        valueLabel.setForeground(TEXT_PRIMARY);
        // Set alignment X on value label.
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Add on card.
        card.add(valueLabel);

        // Add on card.
        card.add(Box.createVerticalStrut(5));

        // Create and store the name label.
        JLabel nameLabel = new JLabel(label);
        // Set font on name label.
        nameLabel.setFont(FONT_BODY);
        // Set foreground on name label.
        nameLabel.setForeground(TEXT_SECONDARY);
        // Set alignment X on name label.
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Add on card.
        card.add(nameLabel);

        // Return card to the caller.
        return card;
    }

    // Define the create styled text field method.
    public static JTextField createStyledTextField() {
        // Create and store the field.
        JTextField field = new JTextField();
        // Set font on field.
        field.setFont(FONT_BODY);
        // Set border on field.
        field.setBorder(BorderFactory.createCompoundBorder(
            // Provide the next argument for set border.
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            // Provide the next argument for set border.
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        // Provide the remaining arguments needed to finish set border.
        ));
        // Return field to the caller.
        return field;
    }

    // Define the create styled password field method.
    public static JPasswordField createStyledPasswordField() {
        // Create and store the field.
        JPasswordField field = new JPasswordField();
        // Set font on field.
        field.setFont(FONT_BODY);
        // Set border on field.
        field.setBorder(BorderFactory.createCompoundBorder(
            // Provide the next argument for set border.
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            // Provide the next argument for set border.
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        // Provide the remaining arguments needed to finish set border.
        ));
        // Return field to the caller.
        return field;
    }

    // Define the create styled combo box box method.
    public static <T> JComboBox<T> createStyledComboBox(T[] items) {
        // Create and store the combo box box.
        JComboBox<T> comboBox = new JComboBox<>(items);
        // Set font on combo box box.
        comboBox.setFont(FONT_BODY);
        // Set background on combo box box.
        comboBox.setBackground(Color.WHITE);
        // Return comboBox to the caller.
        return comboBox;
    }

    // Define the create styled table method.
    public static JTable createStyledTable() {
        // Create and store the table.
        JTable table = new JTable();
        // Set font on table.
        table.setFont(FONT_BODY);
        // Set row height on table.
        table.setRowHeight(35);
        // Set grid color on table.
        table.setGridColor(BORDER_COLOR);
        // Set selection background on table.
        table.setSelectionBackground(new Color(ACCENT.getRed(), ACCENT.getGreen(), ACCENT.getBlue(), 50));
        // Set selection foreground on table.
        table.setSelectionForeground(TEXT_PRIMARY);
        // Get table header on table.
        table.getTableHeader().setFont(FONT_HEADING);
        // Get table header on table.
        table.getTableHeader().setBackground(PRIMARY);
        // Get table header on table.
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        // Set show vertical lines on table.
        table.setShowVerticalLines(false);
        // Set intercell spacing on table.
        table.setIntercellSpacing(new Dimension(0, 1));
        // Return table to the caller.
        return table;
    }
}

