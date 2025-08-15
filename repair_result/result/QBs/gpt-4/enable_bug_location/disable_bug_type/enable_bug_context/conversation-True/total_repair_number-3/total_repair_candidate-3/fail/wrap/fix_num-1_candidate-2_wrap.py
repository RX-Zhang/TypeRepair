def wrap(text, cols):
    lines = []
    while len(text) > cols:
        end = text.rfind(' ', 0, cols + 1)
        if end == -1:
            end = cols
            lines.append(text[:end])
            text = text[end:]
        else:
            line, text = text[:end], text[end + 1:]
            lines.append(line)
    if text:
        lines.append(text)
    return lines
