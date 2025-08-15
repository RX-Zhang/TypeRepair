def wrap(text, cols):
    lines = []
    while len(text) > cols:
        end = text.rfind(' ', 0, cols + 1)
        if end == -1:  # If no space is found, break at the column limit
            end = cols
        line, text = text[:end].rstrip(), text[end:].lstrip()  # Strip whitespace
        lines.append(line)
    lines.append(text.rstrip())  # Append any remaining text
    return lines
