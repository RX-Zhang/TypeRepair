def wrap(text, cols):
    lines = []
    while len(text) > cols:
        end = text.rfind(' ', 0, cols + 1)
        if end == -1:
            end = cols
        line, text = text[:end], text[end:]
        # Strip leading spaces from remaining text to avoid unwanted leading spaces in lines
        text = text.lstrip()
        lines.append(line)
    if text:
        lines.append(text)
    return lines
