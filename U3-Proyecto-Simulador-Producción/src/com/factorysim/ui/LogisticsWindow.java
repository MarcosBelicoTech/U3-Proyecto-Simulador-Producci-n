package com.factorysim.ui;

import com.factorysim.SimulatorController;
import com.factorysim.model.ProductItem;
import com.factorysim.model.Vehicle;
import com.factorysim.model.VehicleType;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class LogisticsWindow extends JFrame {
    private final SimulatorController controller;
    private final JTable vehicleTable;
    private final DefaultListModel<String> transitModel = new DefaultListModel<>();
    private final JProgressBar transitBar = new JProgressBar();
    private final JLabel transitCountLabel = new JLabel("0");
    private final JLabel trucksActiveLabel = new JLabel("0");
    private final JLabel dronesActiveLabel = new JLabel("0");

    public LogisticsWindow(SimulatorController controller) {
        super("🚛 Panel de Logística");
        this.controller = controller;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(780, 480);

        // Fondo general
        getContentPane().setBackground(new Color(240, 242, 247));

        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBackground(new Color(240, 242, 247));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(contentPanel);

        // Encabezado
        JPanel headerPanel = createHeaderPanel();
        contentPanel.add(headerPanel, BorderLayout.NORTH);

        /* -------------------- Tabla de vehículos -------------------- */
        JPanel mainPanel = new JPanel(new BorderLayout(15, 0));
        mainPanel.setOpaque(false);

        vehicleTable = new JTable(new VehicleTableModel(controller.getVehicles()));
        setupVehicleTable();

        JScrollPane tableScrollPane = new JScrollPane(vehicleTable);
        tableScrollPane.setBackground(Color.WHITE);
        tableScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        tableScrollPane.getViewport().setBackground(Color.WHITE);

        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        /* ------------------ Panel lateral derecho ------------------ */
        JPanel rightPanel = createRightPanel();
        mainPanel.add(rightPanel, BorderLayout.EAST);

        contentPanel.add(mainPanel, BorderLayout.CENTER);

        /* ------------------- Panel de métricas --------------------- */
        JPanel metricsPanel = createMetricsPanel();
        contentPanel.add(metricsPanel, BorderLayout.SOUTH);

        /* ------------------- Refresco automático ------------------- */
        new Timer(300, e -> refresh()).start();
    }

    /* --------------------------- Header ---------------------------- */

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel titleLabel = new JLabel("🚛 Centro de Control Logístico");
        titleLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));
        titleLabel.setForeground(new Color(33, 37, 41));

        JLabel subtitleLabel = new JLabel("Monitoreo de flota y entregas en tiempo real");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        subtitleLabel.setForeground(new Color(108, 117, 125));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitleLabel);

        header.add(titlePanel, BorderLayout.WEST);
        return header;
    }

    /* ---------------------- Configurar tabla ----------------------- */

    private void setupVehicleTable() {
        vehicleTable.setRowHeight(40);
        vehicleTable.setFont(new Font("Arial", Font.PLAIN, 12));
        vehicleTable.setGridColor(new Color(222, 226, 230));
        vehicleTable.setSelectionBackground(new Color(232, 245, 255));
        vehicleTable.setSelectionForeground(new Color(33, 37, 41));
        vehicleTable.setShowGrid(true);
        vehicleTable.setIntercellSpacing(new Dimension(1, 1));

        // Encabezado básico (se sobreescribe con renderer personalizado)
        vehicleTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        vehicleTable.getTableHeader().setPreferredSize(new Dimension(0, 35));

        // Renderer de encabezado con texto negro y fondo claro
        vehicleTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                lbl.setOpaque(true);
                lbl.setFont(new Font("Arial", Font.BOLD, 12));
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setBackground(new Color(210, 225, 240));   // celeste suave
                lbl.setForeground(Color.BLACK);                // texto negro
                lbl.setBorder(BorderFactory.createMatteBorder(
                        0, 0, 1, 1, new Color(189, 195, 199)));
                return lbl;
            }
        });

        // Renderer para celdas
        vehicleTable.setDefaultRenderer(Object.class, new VehicleTableCellRenderer());

        // Anchuras de columnas
        vehicleTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        vehicleTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        vehicleTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        vehicleTable.getColumnModel().getColumn(3).setPreferredWidth(120);
    }

    /* ------------------- Panel lateral derecho -------------------- */

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout(0, 15));
        rightPanel.setPreferredSize(new Dimension(280, 0));
        rightPanel.setOpaque(false);

        JPanel transitPanel = new JPanel(new BorderLayout(0, 10));
        transitPanel.setBackground(Color.WHITE);
        transitPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel transitTitle = new JLabel("📦 Productos en Tránsito");
        transitTitle.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        transitTitle.setForeground(new Color(33, 37, 41));

        /* ---- Contador y barra de progreso ---- */
        JPanel countPanel = new JPanel(new BorderLayout(5, 5));
        countPanel.setOpaque(false);

        JLabel countLabel = new JLabel("Total en ruta:");
        countLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        countLabel.setForeground(new Color(108, 117, 125));

        transitCountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        transitCountLabel.setForeground(new Color(33, 150, 243));

        JPanel countSubPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        countSubPanel.setOpaque(false);
        countSubPanel.add(countLabel);
        countSubPanel.add(transitCountLabel);

        countPanel.add(countSubPanel, BorderLayout.NORTH);

        transitBar.setStringPainted(true);
        transitBar.setForeground(new Color(33, 150, 243));
        transitBar.setBackground(new Color(230, 230, 230));
        transitBar.setBorder(BorderFactory.createRaisedBevelBorder());
        countPanel.add(transitBar, BorderLayout.SOUTH);

        /* -------------- Lista de productos -------------- */
        JList<String> transitList = new JList<>(transitModel);
        transitList.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 10));
        transitList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        transitList.setCellRenderer(new TransitListCellRenderer());

        JScrollPane transitScroll = new JScrollPane(transitList);
        transitScroll.setPreferredSize(new Dimension(0, 200));
        transitScroll.setBorder(BorderFactory.createLoweredBevelBorder());

        transitPanel.add(transitTitle, BorderLayout.NORTH);
        transitPanel.add(countPanel, BorderLayout.CENTER);
        transitPanel.add(transitScroll, BorderLayout.SOUTH);

        rightPanel.add(transitPanel, BorderLayout.CENTER);
        return rightPanel;
    }

    /* -------------------- Panel de métricas ------------------------ */

    private JPanel createMetricsPanel() {
        JPanel metricsPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        metricsPanel.setOpaque(false);
        metricsPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JPanel trucksPanel = createVehicleMetricPanel("🚚", "Camiones Activos", trucksActiveLabel,
                new Color(255, 152, 0));

        JPanel dronesPanel = createVehicleMetricPanel("🚁", "Drones Activos", dronesActiveLabel,
                new Color(156, 39, 176));

        metricsPanel.add(trucksPanel);
        metricsPanel.add(dronesPanel);
        return metricsPanel;
    }

    private JPanel createVehicleMetricPanel(String icon, String title,
                                            JLabel valueLabel, Color accentColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        titleLabel.setForeground(Color.BLACK);   // *** TEXTO NEGRO ***

        valueLabel.setFont(new Font("Arial", Font.BOLD, 20));
        valueLabel.setForeground(accentColor);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.add(iconLabel);
        content.add(Box.createVerticalStrut(8));
        content.add(titleLabel);
        content.add(Box.createVerticalStrut(8));
        content.add(valueLabel);

        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    /* ------------------- Refrescar información --------------------- */

    private void refresh() {
        ((AbstractTableModel) vehicleTable.getModel()).fireTableDataChanged();

        /* ---- Productos en tránsito ---- */
        transitModel.clear();
        List<ProductItem> inTransit = controller.getInTransit();
        inTransit.forEach(p -> transitModel.addElement(p.toString()));

        int transitCount = inTransit.size();
        transitCountLabel.setText(String.valueOf(transitCount));

        int maxCapacity = controller.getVehicles().size();
        transitBar.setMaximum(maxCapacity);
        transitBar.setValue(Math.min(transitCount, maxCapacity));
        transitBar.setString(transitCount + " / " + maxCapacity);

        // Color dinámico de la barra
        if (transitCount == 0) {
            transitBar.setForeground(new Color(108, 117, 125));
        } else if (transitCount >= maxCapacity) {
            transitBar.setForeground(new Color(220, 53, 69));
        } else {
            transitBar.setForeground(new Color(33, 150, 243));
        }

        /* ---- Vehículos activos por tipo ---- */
        int trucksActive = 0, dronesActive = 0;
        for (Vehicle v : controller.getVehicles()) {
            if (!v.getStatus().equals("Detenido") && !v.getStatus().equals("Pausado")) {
                if (v.getType() == VehicleType.TRUCK) trucksActive++;
                else dronesActive++;
            }
        }
        trucksActiveLabel.setText(String.valueOf(trucksActive));
        dronesActiveLabel.setText(String.valueOf(dronesActive));
    }

    /* ------------------- Renderer para la tabla -------------------- */

    static class VehicleTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setFont(new Font("Segoe UI Emoji", getFont().getStyle(), getFont().getSize()));

            if (!isSelected) {
                setBackground(row % 2 == 0 ? new Color(248, 249, 250) : Color.WHITE);
            }

            VehicleTableModel model = (VehicleTableModel) table.getModel();
            Vehicle vehicle = model.getVehicle(row);

            switch (column) {
                case 0 -> { // Vehículo
                    setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
                    setForeground(new Color(33, 37, 41));
                    setText("🚗 " + value);
                }
                case 1 -> { // Tipo
                    setFont(new Font("Segoe UI Emoji", Font.PLAIN, 11));
                    String type = value.toString();
                    if (type.equals("Camión")) {
                        setForeground(new Color(255, 152, 0));
                        setText("🚚 " + type);
                    } else {
                        setForeground(new Color(156, 39, 176));
                        setText("🚁 " + type);
                    }
                }
                case 2 -> { // Estado
                    setFont(new Font("Segoe UI Emoji", Font.PLAIN, 11));
                    String status = value.toString();
                    if (status.contains("En ruta")) {
                        setForeground(new Color(40, 167, 69));
                        setText("🟢 " + status);
                    } else if (status.contains("Pausado")) {
                        setForeground(new Color(255, 193, 7));
                        setText("🟡 " + status);
                    } else if (status.contains("Detenido")) {
                        setForeground(new Color(220, 53, 69));
                        setText("🔴 " + status);
                    } else if (status.contains("Entregado")) {
                        setForeground(new Color(23, 162, 184));
                        setText("✅ " + status);
                    } else if (status.contains("Tomando")) {
                        setForeground(new Color(255, 152, 0));
                        setText("📦 " + status);
                    } else {
                        setForeground(new Color(108, 117, 125));
                        setText("⚪ " + status);
                    }
                }
                case 3 -> { // Transportando
                    setFont(new Font("Segoe UI Emoji", Font.PLAIN, 11));
                    if ("—".equals(value.toString())) {
                        setForeground(new Color(108, 117, 125));
                        setText("📦 Vacío");
                    } else {
                        setForeground(new Color(33, 37, 41));
                        String product = value.toString();
                        String emoji = "📦";
                        if (product.contains("A#")) emoji = "🟨";
                        else if (product.contains("B#")) emoji = "🟦";
                        else if (product.contains("C#")) emoji = "🟪";
                        setText(emoji + " " + product);
                    }
                }
            }
            setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 5));
            return this;
        }
    }

    /* ---------------- Renderer para lista de tránsito --------------- */

    static class TransitListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setFont(new Font("Segoe UI Emoji", getFont().getStyle(), getFont().getSize()));

            if (!isSelected) {
                setBackground(index % 2 == 0 ? new Color(248, 249, 250) : Color.WHITE);
            }

            String product = value.toString();
            String emoji = "📦";
            if (product.contains("A#")) {
                emoji = "🟨";
                setForeground(new Color(255, 152, 0));
            } else if (product.contains("B#")) {
                emoji = "🟦";
                setForeground(new Color(33, 150, 243));
            } else if (product.contains("C#")) {
                emoji = "🟪";
                setForeground(new Color(156, 39, 176));
            }
            setText("  " + emoji + " " + product);
            setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
            return this;
        }
    }

    /* ------------------- Modelo de la tabla ------------------------- */

    static class VehicleTableModel extends AbstractTableModel {
        private final String[] cols = {"Vehículo", "Tipo", "Estado", "Transportando"};
        private final List<Vehicle> vehicles;

        VehicleTableModel(List<Vehicle> vehicles) {
            this.vehicles = vehicles;
        }

        Vehicle getVehicle(int row) { return vehicles.get(row); }

        @Override public int getRowCount()           { return vehicles.size(); }
        @Override public int getColumnCount()        { return cols.length;     }
        @Override public String getColumnName(int c) { return cols[c];         }

        @Override
        public Object getValueAt(int r, int c) {
            Vehicle v = vehicles.get(r);
            return switch (c) {
                case 0 -> v.getId();
                case 1 -> v.getType().label;
                case 2 -> v.getStatus();
                case 3 -> v.getCarrying() == null ? "—" : v.getCarrying().toString();
                default -> "";
            };
        }

        @Override public boolean isCellEditable(int r, int c) { return false; }
    }
}
