// ─────────────────────────────────────────────
//  ONLY USED FOR REFERENCE
// ─────────────────────────────────────────────

import java.util.*;
import java.io.*;

// ─────────────────────────────────────────────
//  ASCII CHARACTERS & THEME DEFINITIONS
// ─────────────────────────────────────────────

enum ThemeColor {
    DEFAULT, BOLD, DIM, CYAN, GREEN, YELLOW, RED, MAGENTA, BLUE, WHITE
}

class Theme {
    String name;
    String topBorder;
    String bottomBorder;
    String leftBorder;
    String rightBorder;
    String cornerTL;
    String cornerTR;
    String cornerBL;
    String cornerBR;
    String headerBg;
    String accent;
    String textColor;
    String dimColor;
    String separator;
    String fillChar;
    String emptyChar;

    Theme(String name, String topBorder, String bottomBorder,
          String leftBorder, String rightBorder,
          String cornerTL, String cornerTR, String cornerBL, String cornerBR,
          String headerBg, String accent, String textColor,
          String dimColor, String separator, String fillChar, String emptyChar) {
        this.name = name;
        this.topBorder = topBorder;
        this.bottomBorder = bottomBorder;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.cornerTL = cornerTL;
        this.cornerTR = cornerTR;
        this.cornerBL = cornerBL;
        this.cornerBR = cornerBR;
        this.headerBg = headerBg;
        this.accent = accent;
        this.textColor = textColor;
        this.dimColor = dimColor;
        this.separator = separator;
        this.fillChar = fillChar;
        this.emptyChar = emptyChar;
    }
}

// ─────────────────────────────────────────────
//  THEME FACTORY
// ─────────────────────────────────────────────

class ThemeFactory {
    static List<Theme> createAll() {
        List<Theme> themes = new ArrayList<>();
        themes.add(createDark());
        themes.add(createNeon());
        themes.add(createCyberpunk());
        themes.add(createMatrix());
        themes.add(createMinimal());
        return themes;
    }

    static Theme createDark() {
        return new Theme("DARK",
            "═", "═", "║", "║",
            "╔", "╗", "╚", "╝",
            "█", "▓", "░", "·",
            "─", "█", "░");
    }

    static Theme createNeon() {
        return new Theme("NEON",
            "═", "═", "║", "║",
            "╔", "╗", "╚", "╝",
            "▄", "▀", "░", "·",
            "═", "█", "░");
    }

    static Theme createCyberpunk() {
        return new Theme("CYBERPUNK",
            "─", "─", "|", "|",
            "+", "+", "+", "+",
            "▒", "▓", "░", "·",
            "─", "█", "░");
    }

    static Theme createMatrix() {
        return new Theme("MATRIX",
            "═", "═", "║", "║",
            "╔", "╗", "╚", "╝",
            "█", "▓", "░", "·",
            "─", "█", "░");
    }

    static Theme createMinimal() {
        return new Theme("MINIMAL",
            "─", "─", "│", "│",
            "┌", "┐", "└", "┘",
            " ", "·", " ", "·",
            "─", "█", "░");
    }
}

// ─────────────────────────────────────────────
//  ASCII ART RENDERER
// ─────────────────────────────────────────────

class AsciiRenderer {
    private Theme theme;
    private int width;

    AsciiRenderer(Theme theme, int width) {
        this.theme = theme;
        this.width = width;
    }

    String buildTopBorder(String title) {
        StringBuilder sb = new StringBuilder();
        sb.append(theme.cornerTL);
        int innerWidth = width - 2;
        String titleStr = " " + title + " ";
        int leftPad = (innerWidth - titleStr.length()) / 2;
        int rightPad = innerWidth - titleStr.length() - leftPad;
        sb.append(repeat(theme.topBorder, leftPad));
        sb.append(titleStr);
        sb.append(repeat(theme.topBorder, rightPad));
        sb.append(theme.cornerTR);
        return sb.toString();
    }

    String buildBottomBorder() {
        StringBuilder sb = new StringBuilder();
        sb.append(theme.cornerBL);
        sb.append(repeat(theme.bottomBorder, width - 2));
        sb.append(theme.cornerBR);
        return sb.toString();
    }

    String buildSideLine(String content) {
        StringBuilder sb = new StringBuilder();
        sb.append(theme.leftBorder);
        String padded = content.length() >= width - 2
            ? content.substring(0, width - 2)
            : content + repeat(" ", width - 2 - content.length());
        sb.append(padded);
        sb.append(theme.rightBorder);
        return sb.toString();
    }

    String buildSeparator() {
        StringBuilder sb = new StringBuilder();
        sb.append(theme.leftBorder);
        sb.append(repeat(theme.separator, width - 2));
        sb.append(theme.rightBorder);
        return sb.toString();
    }

    String buildProgressBar(int percent, String label) {
        int barWidth = width - 8;
        int filled = (int) (barWidth * percent / 100.0);
        StringBuilder sb = new StringBuilder();
        sb.append("  ").append(label).append(" [");
        sb.append(repeat(theme.fillChar, filled));
        sb.append(repeat(theme.emptyChar, barWidth - filled));
        sb.append("] ").append(percent).append("%");
        return sb.toString();
    }

    String buildAsciiArt(String art) {
        StringBuilder sb = new StringBuilder();
        for (String line : art.split("\n")) {
            sb.append(buildSideLine(line)).append("\n");
        }
        return sb.toString();
    }

    String buildTable(String[] headers, String[][] data) {
        StringBuilder sb = new StringBuilder();
        int[] colWidths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            colWidths[i] = headers[i].length();
            for (String[] row : data) {
                if (i < row.length) colWidths[i] = Math.max(colWidths[i], row[i].length());
            }
        }
        sb.append(buildSeparator()).append("\n");
        sb.append(buildTableRow(headers, colWidths)).append("\n");
        sb.append(buildSeparator()).append("\n");
        for (String[] row : data) {
            sb.append(buildTableRow(row, colWidths)).append("\n");
        }
        sb.append(buildSeparator());
        return sb.toString();
    }

    private String buildTableRow(String[] cells, int[] colWidths) {
        StringBuilder sb = new StringBuilder();
        sb.append(theme.leftBorder);
        for (int i = 0; i < cells.length; i++) {
            String cell = cells[i];
            int pad = colWidths[i] - cell.length() + (i < cells.length - 1 ? 2 : 1);
            sb.append(" ").append(cell).append(repeat(" ", pad));
        }
        sb.append(theme.rightBorder);
        return sb.toString();
    }

    String renderFullPreview() {
        StringBuilder sb = new StringBuilder();
        sb.append(buildTopBorder("THEME PREVIEW: " + theme.name)).append("\n");
        sb.append(buildSideLine("")).append("\n");
        sb.append(buildSideLine("  Welcome to the Theme Previewer")).append("\n");
        sb.append(buildSideLine("  ─────────────────────────────────")).append("\n");
        sb.append(buildSeparator()).append("\n");
        sb.append(buildSideLine("  " + theme.name + " Theme Configuration")).append("\n");
        sb.append(buildProgressBar(75, "HP")).append("\n");
        sb.append(buildProgressBar(45, "MP")).append("\n");
        sb.append(buildProgressBar(90, "XP")).append("\n");
        sb.append(buildSeparator()).append("\n");

        String[] headers = {"Status", "Value", "Icon"};
        String[][] data = {
            {"Active", "●", "✓"},
            {"Latency", "12ms", "⚡"},
            {"Memory", "64MB", "🧠"},
            {"FPS", "60", "🎮"}
        };
        sb.append(buildTable(headers, data)).append("\n");
        sb.append(buildSeparator()).append("\n");
        sb.append(buildSideLine("  ███╗   ███╗ █████╗ ██████╗  ██████╗ ")).append("\n");
        sb.append(buildSideLine("  ████╗ ████║██╔══██╗██╔══██╗██╔═══██╗")).append("\n");
        sb.append(buildSideLine("  ██╔████╔██║███████║██║  ██║██║   ██║")).append("\n");
        sb.append(buildSideLine("  ██║╚██╔╝██║██╔══██║██║  ██║██║   ██║")).append("\n");
        sb.append(buildSideLine("  ██║ ╚═╝ ██║██║  ██║██████╔╝╚██████╔╝")).append("\n");
        sb.append(buildSideLine("  ╚═╝     ╚═╝╚═╝  ╚═╝╚═════╝  ╚═════╝ ")).append("\n");
        sb.append(buildSideLine("")).append("\n");
        sb.append(buildBottomBorder());
        return sb.toString();
    }

    private String repeat(String s, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) sb.append(s);
        return sb.toString();
    }
}

// ─────────────────────────────────────────────
//  THEME MANAGER
// ─────────────────────────────────────────────

class ThemeManager {
    private List<Theme> themes;
    private int currentIndex;

    ThemeManager() {
        this.themes = ThemeFactory.createAll();
        this.currentIndex = 0;
    }

    Theme getCurrent() {
        return themes.get(currentIndex);
    }

    void next() {
        currentIndex = (currentIndex + 1) % themes.size();
    }

    void previous() {
        currentIndex = (currentIndex - 1 + themes.size()) % themes.size();
    }

    void select(int index) {
        if (index >= 0 && index < themes.size()) {
            currentIndex = index;
        }
    }

    int size() {
        return themes.size();
    }

    List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (Theme t : themes) names.add(t.name);
        return names;
    }
}

// ─────────────────────────────────────────────
//  ANIMATION ENGINE
// ─────────────────────────────────────────────

class AnimationEngine {
    private ThemeManager manager;
    private int fps;

    AnimationEngine(ThemeManager manager, int fps) {
        this.manager = manager;
        this.fps = fps;
    }

    void runLoop(int totalFrames) {
        for (int frame = 0; frame < totalFrames; frame++) {
            clearScreen();
            Theme current = manager.getCurrent();
            AsciiRenderer renderer = new AsciiRenderer(current, 50);
            System.out.println(renderer.renderFullPreview());
            System.out.println();
            System.out.println("  [←/→] Navigate | [1-5] Select | [N] Next | [P] Prev | [Q] Quit");
            System.out.println("  Theme: " + (frame + 1) + "/" + totalFrames +
                " | Current: " + current.name);
            System.out.println();

            try {
                Thread.sleep(1000 / fps);
            } catch (InterruptedException e) {
                break;
            }

            if (System.in.available() > 0) {
                handleInput();
            }
        }
    }

    private void handleInput() {
        try {
            int c = System.in.read();
            switch (c) {
                case 'q': case 'Q':
                    System.out.println("\n  👋 Bye! Theme Previewer shutting down.\n");
                    System.exit(0);
                    break;
                case 'n': case 'N': case 61: // →
                    manager.next();
                    break;
                case 'p': case 'P': case 60: // ←
                    manager.previous();
                    break;
                case '1': case '2': case '3': case '4': case '5':
                    manager.select(c - '1');
                    break;
            }
        } catch (IOException e) { }
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
    }
}

// ─────────────────────────────────────────────
//  MAIN ENTRY POINT
// ─────────────────────────────────────────────

public class ThemePreviewer {
    public static void main(String[] args) {
        System.out.println("\n  ╔══════════════════════════════════╗");
        System.out.println("  ║   ASCII THEME PREVIEWER v1.0   ║");
        System.out.println("  ╚══════════════════════════════════╝\n");

        ThemeManager manager = new ThemeManager();
        List<String> names = manager.getNames();
        System.out.println("  Available Themes:");
        for (int i = 0; i < names.size(); i++) {
            System.out.println("    " + (i + 1) + ". " + names.get(i));
        }
        System.out.println();

        AsciiRenderer initialRenderer = new AsciiRenderer(manager.getCurrent(), 50);
        System.out.println(initialRenderer.renderFullPreview());
        System.out.println("\n  Press any key to start interactive mode...");

        try {
            System.in.read();
        } catch (IOException e) { }

        AnimationEngine engine = new AnimationEngine(manager, 1);
        engine.runLoop(Integer.MAX_VALUE);
    }
}   
// run and compile
// javac ThemePreviewer.java
// java ThemePreviewer   