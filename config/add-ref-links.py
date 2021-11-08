import os
import re

os.chdir('./docs')

DEVELOPER_GUIDE_PATH = "DeveloperGuide.md"
USER_GUIDE_PATH = "UserGuide.md"

SECTION_HEADING_REGEX = "\#+\s*(\d*\.?)*\s([^\#]+)$"

def parse(inputLine):
    m = re.search(SECTION_HEADING_REGEX, inputLine);
    if m is None:
        return None

    inputLine = inputLine.strip()
    heading = m.group(2)

    src = ''.join(e for e in heading if e.isalnum() or e == ' ').strip().replace(' ', '-').lower()
    dest = ''.join(e for e in inputLine if e.isalnum() or e == ' ').strip().replace(" ", "-").lower()
    return {"src": src, "dest": dest, "original": inputLine};


def find_sections(path):
    matches =  []
    f = open(path)
    for line in f:
        match = re.search(SECTION_HEADING_REGEX, line)
        if match is not None:
            matches.append(parse(line.strip()))
    return matches;

def create_refs(parseResult):
    return "[" + parseResult["src"] + "]: [#" + parseResult["dest"]  + "]"

[print(create_refs(i)) for i in find_sections(DEVELOPER_GUIDE_PATH)]

