def levenshtein(source, target):
    if source == '':
        return len(target)
    if target == '':
        return len(source)
    if source[0] == target[0]:
        return levenshtein(source[1:], target[1:])
    return 1 + min(
        levenshtein(source,     target[1:]),
        levenshtein(source[1:], target[1:]),
        levenshtein(source[1:], target)
    )
