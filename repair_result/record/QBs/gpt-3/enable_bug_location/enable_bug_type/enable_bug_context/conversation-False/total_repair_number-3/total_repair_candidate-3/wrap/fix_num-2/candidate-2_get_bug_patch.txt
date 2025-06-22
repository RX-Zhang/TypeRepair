def wrap(text, cols):
    lines = []
    while len(text) > cols:
        end = text.rfind(' ', 0, cols + 1)
        if end == -1:
            end = cols
        line, text = text[:end].strip(), text[end:].lstrip()  # Strip leading/trailing spaces
        lines.append(line)
    lines.append(text.strip())  # Add the last line after exiting the loop
    return lines
