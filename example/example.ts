// ─────────────────────────────────────────────
//  ONLY USED FOR REFERENCE
// ─────────────────────────────────────────────

type ThemeColor = string;

interface ThemeStyle {
  border: string;
  accent: string;
  text: string;
  dim: string;
  background: string;
  separator: string;
}

interface Theme {
  id: string;
  name: string;
  style: ThemeStyle;
  keywords: string[];
}

interface PreviewElement {
  type: "header" | "code" | "status" | "table" | "separator";
  content: string;
}

interface SyntaxToken {
  keyword: string;
  value: string;
  color: ThemeColor;
}

// ─────────────────────────────────────────────
//  THEME DEFINITIONS
// ─────────────────────────────────────────────

const themes: Theme[] = [
  {
    id: "dark",
    name: "DARK MODE",
    style: {
      border: "╔═╗║╚╝",
      accent: "\x1b[38;5;208m",
      text: "\x1b[38;5;252m",
      dim: "\x1b[38;5;240m",
      background: "\x1b[48;5;235m",
      separator: "─",
    },
    keywords: ["const", "let", "function", "return", "class", "interface"],
  },
  {
    id: "neon",
    name: "NEON GLOW",
    style: {
      border: "╔═╗║╚╝",
      accent: "\x1b[38;5;81m",
      text: "\x1b[38;5;225m",
      dim: "\x1b[38;5;245m",
      background: "\x1b[48;5;235m",
      separator: "═",
    },
    keywords: ["const", "let", "function", "return", "class", "interface"],
  },
  {
    id: "cyber",
    name: "CYBERPUNK",
    style: {
      border: "+─+|++",
      accent: "\x1b[38;5;213m",
      text: "\x1b[38;5;255m",
      dim: "\x1b[38;5;242m",
      background: "\x1b[48;5;234m",
      separator: "─",
    },
    keywords: ["const", "let", "function", "return", "class", "interface"],
  },
  {
    id: "matrix",
    name: "MATRIX",
    style: {
      border: "╔═╗║╚╝",
      accent: "\x1b[38;5;46m",
      text: "\x1b[38;5;190m",
      dim: "\x1b[38;5;22m",
      background: "\x1b[48;5;232m",
      separator: "─",
    },
    keywords: ["const", "let", "function", "return", "class", "interface"],
  },
];

// ─────────────────────────────────────────────
//  SYNTAX HIGHLIGHTER CLASS
// ─────────────────────────────────────────────

class SyntaxHighlighter {
  constructor(private theme: Theme) {}

  highlight(code: string): string {
    return code.split("\n").map((line, i) => {
      let highlighted = this.colorizeLine(line);
      const lineNum = String(i + 1).padStart(3, " ");
      return `${this.theme.style.dim}${lineNum}│${"\x1b[0m"}${highlighted}`;
    }).join("\n");
  }

  private colorizeLine(line: string): string {
    let result = line;
    for (const kw of this.theme.keywords) {
      const regex = new RegExp(`\\b${kw}\\b`, "g");
      result = result.replace(regex, `${this.theme.style.accent}${kw}${"\x1b[0m"}`);
    }
    result = result.replace(/"[^"]*"/g, (m) => `${this.theme.style.text}${m}${"\x1b[0m"}`);
    result = result.replace(/\/\/.*/g, (m) => `${this.theme.style.dim}${m}${"\x1b[0m"}`);
    return result;
  }

  tokenize(code: string): SyntaxToken[] {
    const tokens: SyntaxToken[] = [];
    for (const kw of this.theme.keywords) {
      if (code.includes(kw)) {
        tokens.push({ keyword: kw, value: kw, color: this.theme.style.accent });
      }
    }
    return tokens;
  }
}

// ─────────────────────────────────────────────
//  PREVIEW RENDERER CLASS
// ─────────────────────────────────────────────

class PreviewRenderer {
  constructor(private width: number = 55) {}

  render(theme: Theme, highlighter: SyntaxHighlighter): string {
    const parts: string[] = [];
    const s = theme.style;

    parts.push(this.buildHeader(theme));
    parts.push(this.buildSeparator(s));
    parts.push(this.buildSeparator(s));
    parts.push(this.buildStatusPanel(theme));
    parts.push(this.buildFooter(theme));

    return parts.join("\n");
  }

  private buildHeader(theme: Theme): string {
    const s = theme.style;
    const title = ` ${theme.name} SYNTAX PREVIEW `;
    const pad = this.width - title.length - 2;
    const left = Math.floor(pad / 2);
    const right = pad - left;
    return (
      `${s.accent}${s.border[0]}${s.border[1].repeat(left)}${s.text}${title}${s.accent}` +
      `${s.border[1].repeat(right)}${s.border[2]}${"\x1b[0m"}`
    );
  }

  private buildSeparator(s: ThemeStyle): string {
    return `${s.accent}${s.border[3]}${s.separator.repeat(this.width - 2)}${s.border[3]}${"\x1b[0m"}`;
  }

  

  private buildStatusPanel(theme: Theme): string {
    const s = theme.style;
    const rows = [
      ["Theme", theme.name, "●"],
      ["Keywords", String(theme.keywords.length), "⚡"],
      ["Border", s.border[1], "▓"],
      ["Accent", "ACTIVE", "✓"],
    ];
    let out = "";
    for (const [label, val, icon] of rows) {
      const line = `  ${s.text}${label.padEnd(12)}${s.accent}${val.padEnd(10)}${icon}`;
      out += `${s.border[3]} ${line.padEnd(this.width - 4)} ${s.border[3]}\n`;
    }
    return out;
  }

  private buildFooter(theme: Theme): string {
    const s = theme.style;
    return `${s.accent}${s.border[4]}${s.border[1].repeat(this.width - 2)}${s.border[5]}${"\x1b[0m"}`;
  }
}

// ─────────────────────────────────────────────
//  THEME MANAGER CLASS
// ─────────────────────────────────────────────

class ThemeManager {
  private current: number = 0;

  constructor(private themes: Theme[]) {}

  get currentTheme(): Theme {
    return this.themes[this.current];
  }

  next(): void {
    this.current = (this.current + 1) % this.themes.length;
  }

  previous(): void {
    this.current = (this.current - 1 + this.themes.length) % this.themes.length;
  }

  select(index: number): void {
    if (index >= 0 && index < this.themes.length) {
      this.current = index;
    }
  }

  list(): string[] {
    return this.themes.map((t, i) => `  ${i + 1}. ${t.name}`);
  }
}

// ─────────────────────────────────────────────
//  FUNCTIONS
// ─────────────────────────────────────────────


function printBanner(): void {
  console.log(`\n\x1b[38;5;208m  ╔═══════════════════════════════════════╗\x1b[0m`);
  console.log(`\x1b[38;5;208m  ║   ⚡ TYPESCRIPT THEME PREVIEWER    ║\x1b[0m`);
  console.log(`\x1b[38;5;208m  ╚═══════════════════════════════════════╝\x1b[0m\n`);
}

function renderPreview(theme: Theme, renderer: PreviewRenderer): void {
  const highlighter = new SyntaxHighlighter(theme);
  const output = renderer.render(theme, highlighter);
  console.log(output);
}

function showControls(): void {
  console.log("\n  [1-4] Select | [N]ext | [P]rev | [Q]uit");
}

// ─────────────────────────────────────────────
//  MAIN
// ─────────────────────────────────────────────

function main(): void {
  printBanner();

  const manager = new ThemeManager(themes);
  const renderer = new PreviewRenderer(55);

  console.log("  Available Themes:");
  manager.list().forEach((l) => console.log(l));
  console.log();

  renderPreview(manager.currentTheme, renderer);
  showControls();

}

main();   