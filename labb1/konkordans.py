import sys

word = sys.argv[1]
kth = False

my_index_path = "labb1/index.txt"
my_korpus_path = "labb1/korpus"

three_letter_path = "labb1/three_letter.txt"

kth_default_path = "/afs/kth.se/misc/info/kurser/DD2350/adk23/labb1/"
kth_index_path = kth_default_path + "rawindex.txt"
kth_korpus_path = kth_default_path + "korpus"

index_path = my_index_path if not kth else kth_index_path
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
    load_three_letter()

    word = word.lower()
    word_prefix = word[:3]

    i = three_letter_data[word_prefix]
    j = three_letter_data[get_next_entry(three_letter_data, word_prefix)]

    with open(index_path, "r", encoding="latin-1") as f:
        print("1: ", time() - start_time)
        while j - i > 1000:
            m = (i + j) // 2

            f.seek(m)
            f.readline()

            s = f.readline().split()[0]

            if s <= word:
                i = m
            else:
                j = m

        print("2: ", time() - start_time)

        f.seek(i)
        f.readline()  # Prevent list index out of range

        print("3: ", time() - start_time)
        while True:
            line = f.readline()
            line_word = line.split()[0]
            if line_word == word:
                print("Time to search: ", time() - start_time)

                # now we want to read the file backwards until we find a line that does not contain the word
                # then we want to read line by line until we find a line that does contain the word
                # then we want to read the rest of the file line by line until we find a line that does not contain the word

                occurrences = []
                buffer_size = 100000  # You can adjust the buffer size as needed
                new_pos = i

                start_time = time()

                while True:
                    # Read a chunk from the file
                    new_pos = max(f.tell() - buffer_size, 0)

                    f.seek(new_pos)

                    f.readline()
                    line = f.readline()

                    try:
                        found_word = line.split()[0]

                    except ValueError:
                        print(line)

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
                                first_positon = f.tell() - len(line)
                                break

                        if first_positon:
                            break

                print("Time to find first position: ", time() - start_time)

                f.seek(first_positon)

                count = int(f.readline().split()[2])
                print(
                    "There are",
                    count,
                    "occurrences of the word",
                    word,
                    "in the text.",
                )

                for i in range(25):
                    line = f.readline().strip()
                    found_word, position = line.split()[0], line.split()[1]

                    if found_word == word:
                        occurrences.append(int(position))

                with open(korpus_path, "r", encoding="latin-1") as f:
                    for key in occurrences:
                        f.seek(max(0, key - context_length))
                        text = f.read(total_length).replace("\n", " ").strip()
                        print(text)

                if count > 25:
                    print("Do you want to print all occurrences? (y/n)")

                    answer = sys.stdin.readline().strip()
                    if answer == "n":
                        sys.exit(0)

                    elif answer == "y":
                        with open(index_path, "r", encoding="latin-1") as f:
                            f.seek(first_positon)
                            while True:
                                line = f.readline().strip()
                                found_word, position = line.split()[0], line.split()[1]

                                if found_word == word:
                                    occurrences.append(int(position))

                                else:
                                    break

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
            for key in words:
                f.seek(max(0, key - context_length))
                text = f.read(total_length).replace("\n", " ").strip()
                print(text)

else:
    print("There are", len_words, "occurrences of the word", word, "in the text.")
    with open(korpus_path, "r", encoding="latin-1") as f:
        for key in words:
            f.seek(max(0, key - context_length))
            text = f.read(total_length).replace("\n", " ").strip()
            print(text)

# construct()

"""

HELP FUNCTION FOR COUNTING WORD INSTANCES IN RAWINDEX.TXT TO MAKE SURE THAT THE RESULTS ARE CORRECT
(And they are ofc :) )

def count_word_instances(input_file_path, output_file_path):
    word_count = {}
    with open(input_file_path, 'r', encoding="latin-1") as infile:
        for line in infile:
            word, _ = line.strip().split()
            word_count[word] = word_count.get(word, 0) + 1

    with open(output_file_path, 'w', encoding="latin-1") as outfile:
        for word, instances in word_count.items():
            outfile.write(f"{word} {instances}\n")

# Example usage:
input_file_path = "labb1/rawindex.txt"
output_file_path = "labb1/rawindex_instances.txt"
count_word_instances(input_file_path, output_file_path)
"""
