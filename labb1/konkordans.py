import sys
import time

word = sys.argv[1]

def construct():
    # Initialize an empty dictionary to store the unique three-letter prefixes and their positions
    three_letter_prefixes = {}
    
    print("Reading input file...")

    # Read the input file
    with open('labb1/rawindex.txt', 'r', encoding="latin-1") as infile:
        for line in infile:
            word, position = line.strip().split()
            position = int(position)  # Convert the position from string to integer
            
            # Take the first 3 letters from each word
            prefix = word[:3]
            
            # If the prefix is not in the dictionary, add it along with its position
            if prefix not in three_letter_prefixes:
                three_letter_prefixes[prefix] = position

    # Write the output file
    with open('three_letter.txt', 'w') as outfile:
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
- Indexarray A[abc] with format: <abc> <position in I>]"""
def search(word):
    from time import time
    start_time = time()
    load_three_letter("labb1/three_letter.txt")
    end_time_three_letter = time()
    print("Time to load three_letter.txt: ", end_time_three_letter - start_time)
    
    word_prefix = word[:2]
    i = three_letter_data[word_prefix]
    j = three_letter_data[get_next_entry(three_letter_data, word_prefix)]
    
    with open("labb1/rawindex.txt", "r", encoding="latin-1") as f:
        while i - j > 1000:
            m = (i + j) / 2
            f.seek(m)
            s = f.readline().split()[0]
            if s <= word:
                i = m
            else:
                j = m
    
        f.seek(i)
    
        while True:
            s = f.readline()
            if s.startswith(word):
                x = s.split()[1]
                print("Time to search: ", time() - start_time)
                return x
            elif s > word:
                return None
    
print(search(word))
    