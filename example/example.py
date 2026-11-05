# THIS test.py ONLY USED FOR SEEING HOW IT LOOKS IN DIFFERENT THEME.

import os
import json
import anthropic
from pathlib import Path

# 1. Setup and Configuration
client = anthropic.Anthropic(api_key=os.environ.get("ANTHROPIC_API_KEY", "your_key_here"))
SYSTEM_PROMPT = "You are a helpful assistant that edits files."

def resolve_path(path: str) -> Path:
    """Resolve absolute path safely."""
    return Path(path).resolve()

def read_file_tool(filepath: str) -> str:
    """Reads the content of a file."""
    try:
        p = resolve_path(filepath)
        if not p.exists():
            return f"Error: {filepath} not found."
        return p.read_text(encoding="utf-8")
    except Exception as e:
        return f"Error reading file: {str(e)}"

def list_files_tool(directory: str = ".") -> str:
    """Lists files in a directory."""
    try:
        p = resolve_path(directory)
        if not p.is_dir():
            return f"Error: {directory} is not a directory."
        return "\n".join([f.name for f in p.iterdir()])
    except Exception as e:
        return f"Error listing files: {str(e)}"

def edit_file_tool(filepath: str, old_str: str, new_str: str) -> str:
    """Replaces first occurrence of old_str with new_str."""
    try:
        p = resolve_path(filepath)
        if not p.exists():
            return f"Error: {filepath} not found."
        
        content = p.read_text(encoding="utf-8")
        if old_str == "":
            p.write_text(new_str, encoding="utf-8")
            return f"Created/Overwrote {filepath}"
        
        if old_str not in content:
            return f"Error: '{old_str}' not found in {filepath}"
            
        new_content = content.replace(old_str, new_str, 1)
        p.write_text(new_content, encoding="utf-8")
        return f"Edited {filepath}"
    except Exception as e:
        return f"Error editing file: {str(e)}"

# 2. Tool Registry
TOOLS = {
    "read_file": read_file_tool,
    "list_files": list_files_tool,
    "edit_file": edit_file_tool
}

def extract_tool_calls(response: str) -> list:
    """
    Simple parser for tool calls in LLM response.
    Expects format: <tool_call>tool_name</tool_call><args>json</args>
    This is a simplified parser for demonstration.
    """
    import re
    tool_pattern = r'   