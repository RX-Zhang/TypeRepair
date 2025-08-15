def wrap(text, cols):
    lines = []
    while len(text) > cols:
        end = text.rfind(' ', 0, cols + 1)
        if end == -1:
            end = cols
        line, text = text[:end], text[end:]
        if text and text[0] == ' ':
            text = text[1:]  # remove leading space
        lines.append(line)
    if text:
        lines.append(text)
    return lines
