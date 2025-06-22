def wrap(text, cols):
    lines = []
    while len(text) > cols:
        end = text.rfind(' ', 0, cols + 1)
        if end == -1:
            end = cols
        line, text = text[:end].rstrip(), text[end:].lstrip()  # Remove trailing spaces from line and leading spaces from text
        lines.append(line)
    lines.append(text.rstrip())  # Append the remaining text after the loop, removing any trailing spaces
    return lines
