import re
import sys
<<<<<<< HEAD
=======
import time
from file_read_backwards import FileReadBackwards
>>>>>>> ad32071 (main functionality working)


def validate_and_get_word():
    word = sys.argv[1]

<<<<<<< HEAD
    if not re.match("^[a-öA-Ö]+$", word):
        print(f"'{word}' is not a valid word. Please input only alphabetic characters.")
        sys.exit(1)

    return word
=======
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
>>>>>>> ad32071 (main functionality working)

word = validate_and_get_word()

# True == KTH, False == home
kth = False

my_index_path = "labb1/index.txt"
my_korpus_path = "labb1/korpus"
three_letter_path = "labb1/three_letter.txt"

kth_default_path = "/afs/kth.se/misc/info/kurser/DD2350/adk23/labb1/"
kth_index_path = kth_default_path + "rawindex.txt"
kth_korpus_path = kth_default_path + "korpus"

index_path = my_index_path
korpus_path = my_korpus_path if not kth else kth_korpus_path

word_length = len(word)
context_length = 30
total_length = word_length + context_length * 2

# Help function for loading the three_letter.txt file into a dictionary
three_letter_data = {}
def load_three_letter():
    # Read the output file
    with open(three_letter_path, "r", encoding="latin-1") as f:
        for line in f:
            prefix, position = line.strip().split()
            position = int(position)  # Convert the position from string to integer
            three_letter_data[prefix] = position


# Help function for getting the next entry in a dictionary
def get_next_entry(dictionary, key):
    temp = list(dictionary)
    try:
        res = temp[temp.index(key) + 1]
    except (ValueError, IndexError):
        res = temp[temp.index(key)]

    return res


"""Searches for a word
Requires:
- Big file L
- Index I with format: <word> <position in L>
- Indexarray A[abc] with format: <abc> <position in I>]

Returns: The first position of the word in L"""
<<<<<<< HEAD


=======
>>>>>>> ad32071 (main functionality working)
def search(word):
    first_position = 0
    from time import time

    start_time = time()
<<<<<<< HEAD
    load_three_letter()

    word = word.lower()
    word_prefix = word[:3]

    # Load in positions associated with the prefix and the next prefix
    i = three_letter_data[word_prefix]
    j = three_letter_data[get_next_entry(three_letter_data, word_prefix)]

    # Binary search
    with open(index_path, "r", encoding="latin-1") as f:

        # Binary search until we're close enough to the word
        while j - i > 1000:
            # Middle of file
            m = (i + j) // 2

            f.seek(m)
            f.readline()

            s = f.readline().split()[0]

=======
    load_three_letter("labb1/three_letter.txt")
    end_time_three_letter = time()
    
    word_prefix = word[:3]
    i = three_letter_data[word_prefix]
    j = three_letter_data[get_next_entry(three_letter_data, word_prefix)]
    
    with open("labb1/rawindex.txt", "r", encoding="latin-1") as f:
        """
        while j-i > 1000:
            
            m = (i + j) // 2
            
            f.seek(m)
            f.readline()
            
            s = f.readline().split()[0]
            
>>>>>>> ad32071 (main functionality working)
            if s <= word:
                i = m
            else:
                j = m
<<<<<<< HEAD

        f.seek(i)
        f.readline()  # Prevent list index out of range (can be in the middle of a line)

        # Linear search
        while True:
            line = f.readline()
            
            try:
                line_word = line.split()[0]
            except IndexError:
                # reached last line in file
                break
            
            if line_word == word:
                print("Time to search: ", time() - start_time)

                # now we want to read the file backwards until we find a line that does not contain the word
                # then we want to read line by line until we find a line that does contain the word
                # then we want to read the rest of the file line by line until we find a line that does not contain the word

                occurrences = []
                buffer_size = 10000
                new_pos = i

                checked_start_of_file = False


                while True:
                    new_pos = max(f.tell() - buffer_size, 0)
                    f.seek(new_pos)

                    # Read partial line to ensure next readline starts at a full line
                    f.readline()

                    # Check if we've reached the beginning of the file
                    if new_pos == 0:
                        if checked_start_of_file:
                            break  # Exit if we've already checked the start of the file
                        checked_start_of_file = True

                    line = f.readline()

                    found_word = line.split()[0]

                    # If we still find the right word we have not yet found the first occurance
                    if found_word != word:
                        # We have found a line that does not contain the word
                        # Go forward until we find a line that does contain the word
                        while True:
                            line = f.readline()
                            if not line:
                                break

                            found_word = line.split()[0]

                            if found_word == word:
                                first_position = f.tell() - len(line)
                                break

                        if first_position:
                            break

                f.seek(first_position)

                word, position, count = f.readline().strip().split()
                
                position = int(position)
                count = int(count)
                
                occurrences.append(int(position))
                
                print(
                    "There are",
                    count,
                    "occurrences of the word",
                    word,
                    "in the text.",
                )

                # Print up to 25 occurrences
                for i in range(25 if count > 25 else count):
                    line = f.readline().strip()
                    
                    # last line in file
                    if line == "":
                        break
                    
                    found_word, position = line.split()[0], line.split()[1]

                    if found_word == word:
                        occurrences.append(int(position))

                with open(korpus_path, "r", encoding="latin-1") as f:
                    for key in occurrences:
                        f.seek(max(0, key - context_length))
                        text = f.read(total_length).replace("\n", " ").strip()
                        print(text)

                # If there are more than 25 occurrences, ask the user if they want to print them all
                if count > 25:
                    print("Do you want to print all occurrences? (y/n)")

                    answer = sys.stdin.readline().strip()
                    if answer == "n":
                        sys.exit(0)

                    elif answer == "y":
                        with open(index_path, "r", encoding="latin-1") as f:
                            f.seek(first_position)

                            # Store all occurrences in a list
                            while True:
                                line = f.readline().strip()
                                found_word, position = line.split()[0], line.split()[1]

                                if found_word == word:
                                    occurrences.append(int(position))

                                else:
                                    break

                        # Print all occurrences from the list with context
                        with open(korpus_path, "r", encoding="latin-1") as f:
                            for key in occurrences:
                                f.seek(max(0, key - context_length))
                                text = f.read(total_length).replace("\n", " ").strip()
                                print(text)

                sys.exit(0)
            elif line_word > word:
                print("The word", word, "does not exist in the text.")
                sys.exit(0)


words = search(word)


if words == None:
    print("The word", word, "does not exist in the text.")
    sys.exit(0)

len_words = len(words)


if len(words) > 25:
    print(
        "There are",
        len_words,
        "occurrences of the word",
        word,
        "in the text. Do you want to print them all? (y/n)",
    )

    answer = sys.stdin.readline().strip()
    if answer == "n":
        sys.exit(0)

    elif answer == "y":
        with open(korpus_path, "r", encoding="latin-1") as f:
            # Print until there are no more occurrences
            for key in words:
                f.seek(max(0, key - context_length))
                text = f.read(total_length).replace("\n", " ").strip()
                print(text)

# If the number of occurrences is less than 25, print them all
else:
    print("There are", len_words, "occurrences of the word", word, "in the text.")
    with open(korpus_path, "r", encoding="latin-1") as f:
        for key in words:
            f.seek(max(0, key - context_length))
            text = f.read(total_length).replace("\n", " ").strip()
            print(text)
=======
        """
            
        f.seek(i)
        f.readline() # Prevent list index out of range
        while True:
            line = f.readline()
            line_word = line.split()[0]
            if line_word == word:
                print("Time to search: ", time() - start_time)
                
                occurances = []
                
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
word_length = len(word)
context_length = 20
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
                text = f.read(total_length)
                print(text)
>>>>>>> ad32071 (main functionality working)
