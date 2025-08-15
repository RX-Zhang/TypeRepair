def levenshtein(source, target):
    if source == '' or target == '':
        return len(source) + len(target)  # Corrected logical error
    elif source[0] == target[0]:
        return levenshtein(source[1:], target[1:])  # Removed +1
    else:
        return 1 + min(
            levenshtein(source,     target[1:]),
            levenshtein(source[1:], target[1:]),
            levenshtein(source[1:], target)
        )
