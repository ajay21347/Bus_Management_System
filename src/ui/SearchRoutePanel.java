package ui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.PathResult;
import service.RouteService;
import service.StopService;
import util.CSVExporter;
import util.TimeUtil;

public class SearchRoutePanel extends JPanel {

    private final RouteService routeService = new RouteService();
    private final StopService stopService = new StopService();

    public SearchRoutePanel() {

        setLayout(new BorderLayout(10, 10));

        // ================= INPUT PANEL =================
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));

        JTextField srcField = new JTextField();
        JTextField destField = new JTextField();

        String[] filters = {"ALL", "FASTEST", "LEAST_STOPS"};
        JComboBox<String> filterBox = new JComboBox<>(filters);

        JButton searchBtn = new JButton("Find Routes");
        JButton exportBtn = new JButton("Export CSV");

        JPopupMenu srcPopup = new JPopupMenu();
        JPopupMenu destPopup = new JPopupMenu();

        inputPanel.add(new JLabel("Source Stop"));
        inputPanel.add(srcField);
        inputPanel.add(new JLabel("Filter"));
        inputPanel.add(filterBox);

        inputPanel.add(new JLabel("Destination Stop"));
        inputPanel.add(destField);
        inputPanel.add(searchBtn);
        inputPanel.add(exportBtn);

        // ================= TABLE =================
        String[] cols = {"Path", "Time", "Distance (km)", "Bus", "Driver"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        JTable table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // ⭐ Highlight shortest route
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (row == 0) {
                    c.setBackground(new Color(204, 255, 204)); // light green
                } else {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        });

        // ================= AUTO-SUGGEST =================
        autoSuggest(srcField, srcPopup);
        autoSuggest(destField, destPopup);

        // ================= SEARCH =================
        searchBtn.addActionListener(e -> {
            model.setRowCount(0);

            int srcId = stopService.getStopId(srcField.getText());
            int destId = stopService.getStopId(destField.getText());

            if (srcId == -1 || destId == -1) {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid stop name!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            String filter = filterBox.getSelectedItem().toString();

            List<PathResult> paths =
                    routeService.findRoutes(srcId, destId, filter);

            for (PathResult p : paths) {
                model.addRow(new Object[]{
                        String.join(" → ", p.getPath()),
                        TimeUtil.toHoursMinutes(p.getTotalTime()),
                        p.getTotalDistance(),
                        p.getBus().getBusName(),
                        p.getBus().getDriverName()
                });
            }

            // ================= EXPORT =================
            exportBtn.addActionListener(ev -> CSVExporter.export(paths));
        });

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    // ================= AUTO-SUGGEST HELPER =================
    private void autoSuggest(JTextField field, JPopupMenu popup) {

        field.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { show(); }
            public void removeUpdate(DocumentEvent e) { show(); }
            public void changedUpdate(DocumentEvent e) { show(); }

            private void show() {
                popup.removeAll();
                String text = field.getText();

                if (text.length() < 2) {
                    popup.setVisible(false);
                    return;
                }

                List<String> suggestions =
                        stopService.suggestStops(text);

                if (suggestions.isEmpty()) {
                    popup.setVisible(false);
                    return;
                }

                for (String s : suggestions) {
                    JMenuItem item = new JMenuItem(s);
                    item.addActionListener(e -> {
                        field.setText(s);
                        popup.setVisible(false);
                    });
                    popup.add(item);
                }

                popup.show(field, 0, field.getHeight());
            }
        });
    }
}
