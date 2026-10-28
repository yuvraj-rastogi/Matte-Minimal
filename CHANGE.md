# Change Log

Refer to [CHANGELOG.md](file:///home/yuvraj/Documents/Projects/cursor-original-theme/CHANGELOG.md) for detailed notes on each release.

## Version 1.0.5
- **VS Code OLED Loading Fix**: Fixed theme contribution path in `package.json` so `@vscode - Vscode 2026 OLED` loads properly when selected.
- **Pure Black OLED Canvas**: Panels, navbar, sidebar, status bar, title bar, tabs, and widgets set to `#000000`, matching `cursor-oled`.
- **Grey Chrome Borders**: Added subtle grey borders (`#2a2b2c`) separating panels, navigation bars, and tabs.
- **Italic Comments**: Comments set to italic without modifying VS Code's original syntax text colors.

## Version 1.0.3
- **Panel & Chrome Borders**: Added subtle grey borders (`#1a1a1a`) across sidebars, panels, status bar, title bar, tabs, and editor groups matching `cursor-oled`.
- **Fixed Pink Hover & Selection**: Removed solid opaque pink `#f75f8f` hover background, bracket match background, and list selection. Replaced with translucent grey-white (`#F0F0F01E`) and neutral dark grey (`#303030`, `#262626`) from `cursor-oled` so text remains clearly visible.

## Version 1.0.2
- **Vercel Dark Syntax Palette**: Restored core colors from `_gemini/sample.json`.
- **Parameter & Signature Italics**: Italic styling for method parameters, index signatures (`[key: string]`), constructor parameters, and `this`.
- **Comment `@abc` Tag Styling**: Italic comments with `#43AAF9` Cyan/Blue for `@abc` comment tags.
- **Focused Bold Styling**: Limited bolding exclusively to function and method declarations per `cursor-oled`.

## Version 1.0.1
- **Vercel Dark Color Separation**: Solved the monochromatic blue issue in TypeScript. Custom type aliases in Amber Gold (`#F5A623`), utility types in Purple (`#bf7af0`), primitive types in Mint Cyan (`#50E3C2`), and operators in Pink (`#f75f8f`).
- **File Tab Active Border**: Updated active tab border from blue to signature Vercel Pink (`#f75f8f`).
- **Comments `@tag` Styling**: Both `@` and tag name (e.g. `@abc`, `@ikd`) now render in Cyan/Blue (`#43AAF9`) italic.
- **Hover & Highlight Bug**: Replaced opaque pink hover block with subtle `#43AAF926` highlight.
- **Section Borders & OLED Canvas**: Pure `#000000` with subtle `#1a1a1a` section borders.

## Version 1.0.0
- **Cursor Collection**: 8 authentic Cursor IDE color themes ported for VS Code (including Cursor OLED, Dark, Midnight, High Contrast, Colorblind, and Light variants).
- **Vercel Collection**: Vercel Dark and Vercel Light themes inspired by Vercel's Geist design language.
- **Orgin Signature Themes**: Custom `@orgin` OLED and Dark editions with vibrant electric accents.
- **True OLED Black & Gray Selection**: Full `#000000` IDE canvas with balanced neutral grey selections and light grey editor text for optimal eye comfort.
- **Smart Quick Pick Filtering**: Instant category search using `@cursor`, `@vercel`, and `@orgin`.
- **Open Source on GitHub**: Free to use, fork, and inspect under the protective Anti-Plagiarism & Non-Commercial License.
