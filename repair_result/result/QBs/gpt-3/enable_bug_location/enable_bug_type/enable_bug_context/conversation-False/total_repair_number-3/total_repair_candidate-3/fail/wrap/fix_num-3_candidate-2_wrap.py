def wrap(text, cols):
    lines = []
    while len(text) > cols:
        end = text.rfind(' ', 0, cols + 1)
        if end == -1:  # No space found, break at cols
            end = cols
        line, text = text[:end].rstrip(), text[end:].lstrip()  # Strip whitespace
        lines.append(line)
    lines.append(text.rstrip())  # Add remaining text
    return lines
