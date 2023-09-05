import sys

word = sys.argv[1]

def construct():
    # Initialize an empty dictionary to store the unique three-letter prefixes and their positions
    three_letter_prefixes = {}
    
    print("Reading input file...")

    # Read the input file
    with open('labb1/rawindex.txt', 'r', encoding="latin-1") as infile:
        while True:
            position = infile.tell()  # Get the position of the file pointer
            line = infile.readline()
            if not line:
                break
            
            word = line.strip().split()[0]
            
            # Take the first 3 letters from each word
            prefix = word[:3]
            
            # If the prefix is not in the dictionary, add it along with its position
            if prefix not in three_letter_prefixes:
                three_letter_prefixes[prefix] = position

    # Write the output file
    with open('labb1/three_letter.txt', 'w') as outfile:
        for prefix, position in three_letter_prefixes.items():
            outfile.write(f"{prefix} {position}\n")

    print("Output file has been created.")
    
three_letter_data = {}
def load_three_letter(textfile):

    # Read the output file
    with open(textfile, 'r', encoding="latin-1") as f:
        for line in f:
            prefix, position = line.strip().split()
            position = int(position)  # Convert the position from string to integer
            three_letter_data[prefix] = position
            
def get_next_entry(dictionary, key):
    temp = list(dictionary)
    try:
        res = temp[temp.index(key) + 1]
    except (ValueError, IndexError):
        res = None
        
    return res


"""Searches for a word
Requires:
- Big file L
- Index I with format: <word> <position in L>
- Indexarray A[abc] with format: <abc> <position in I>]

Returns: The first position of the word in L"""
def search(word):
    from time import time
    start_time = time()
    load_three_letter("labb1/three_letter.txt")
    
    word_prefix = word[:3]
    i = three_letter_data[word_prefix]
    j = three_letter_data[get_next_entry(three_letter_data, word_prefix)]
    
    with open("labb1/rawindex.txt", "r", encoding="latin-1") as f:
        while j-i > 1000:
            
            m = (i + j) // 2
            
            f.seek(m)
            f.readline()
            
            s = f.readline().split()[0]
            
            if s <= word:
                i = m
            else:
                j = m
            
        f.seek(i)
        f.readline() # Prevent list index out of range
        while True:
            line = f.readline()
            line_word = line.split()[0]
            if line_word == word:
                print("Time to search: ", time() - start_time)
                
                occurances = []
                
                buffer_size = 256  # You can adjust the buffer size as needed
                buffer = ""
                
                while True:
                    # Read a chunk from the file
                    new_pos = max(f.tell() - buffer_size, 0)
                    f.seek(new_pos)
                    chunk = f.read(buffer_size)
                    
                    # jump back to the old position
                    f.seek(new_pos)
                    
                    buffer = buffer + chunk
                    
                    # Look for line breaks
                    lines = buffer.split("\n")
                    
                    # The last line in the list is either the rest of the buffer or an empty string
                    # We keep it in the buffer for the next iteration
                    buffer = lines.pop()
                    lines = lines[1:]

                    correct_word = True
                    
                    # Process lines
                    for line in reversed(lines):
                        print(line)
                        if line.split()[0] == word:
                            occurances.append(int(line.split()[1]))
                            
                        else:
                            correct_word = False 
                            break
                        
                    if correct_word == False:
                        break
                        
                        
                f.seek(i)
                f.readline()
                    
                
                while True:
                    line = f.readline()
                    if not line:
                        break
                    
                    found_word, position = line.split()
                    
                    if found_word == word:
                        occurances.append(int(position))
                        
                    else: break
                
                return occurances
            elif line_word > word:
                return None
   
#construct() 
words = search(word)

if words == None:
    print("The word", word, "does not exist in the text.")
    sys.exit(0)

word_length = len(word)
context_length = 30
total_length = word_length + context_length * 2
len_words = len(words)

if len(words) > 25:
    print("There are", len_words, "occurences of the word", word, "in the text. Do you want to print them all? (y/n)")
    import sys
    
    answer = sys.stdin.readline().strip()
    if answer == "n":
        sys.exit(0)
        
    elif answer == "y":

        with open("labb1/korpus", "r", encoding="latin-1") as f:
            for key in words:
                f.seek(max(0, key - context_length))
                text = f.read(total_length).replace("\n", " ").strip()
                print(text)