# Command Line Tool: `txted`

## Descriptions

Program txted performs basic text transformations on lines of text from an input FILE. Unless the -f option (see below) is specified, the program writes transformed text to stdout and errors/usage messages to stderr.  The FILE parameter is required and must be the last parameter. OPTIONS may be zero or more of the following and may occur in any order:

- -f
Edit file in place.  The program overwrites the input file with transformed text instead of writing to stdout.
- -e `<string>`
Exclude any lines containing the given string.
- -i 
Used with the -e flag ONLY; applies case insensitive matching.
- -s `<integer>`
Skip either the even or odd lines in a file, with 0 being even and 1 being odd.
- -x `<string>`
Adds string as a suffix to each line.
- -r 
Reverse the order of lines in a file -- the last line is first, the first line is last, and so on.
- -n `<integer>`
Add a line number field followed by a single space to the beginning of each line output.  The line number field shall be left-padded with 0 or left-truncated as required to the width specified by the integer parameter.  Line numbering should start at 1.

## Notes

- While the last command-line parameter provided is always treated as the filename, OPTIONS flags can be provided in any order and will be applied as follows:
	- Options -f and -i should be processed first, as they determine global parameters of the computation.
	- Options -s, -e, -x, -r, and -n should be processed in this order. That is: (1) if -s is present, then file content is filtered based on the specified parameter; (2) if -e is present, then file content excludes lines that include the specified parameter; (3) if -x is present, then a suffix should be applied; (4) if -r is present, then reversal of line order logic is performed; (5) if -n is present, then padded line numbering should be applied.
- Specifying option -i without having specified option -e should result in an error.
- Specifying option -e or -x with an empty string parameter should result in an error.
- Specifying option -n with an integer <0 should result in an error.
- Specifying option -s with an input parameter not equal to 0 or 1 should result in an error.  Each file starts with line 1, which should be considered odd.
If options are repeated, only their last occurrence is considered.
- All program option parameters are required and will result in an error if omitted.
- You should assume that the `<string>` parameters will not contain newlines, as the behavior of the program is platform dependent and undefined in those cases.
- You should assume that the last line of the input file will be newline-terminated. Otherwise, program behavior is undefined.
	- The only exception would be an empty input file which should produce an empty output with no options executed.

