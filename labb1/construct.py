kth = False

my_index_path = "labb1/rawindex.txt"
my_korpus_path = "labb1/korpus"

three_letter_path = "labb1/three_letter.txt"

kth_default_path = "/afs/kth.se/misc/info/kurser/DD2350/adk23/labb1/"
kth_index_path = kth_default_path + "rawindex.txt"
kth_korpus_path = kth_default_path + "korpus"

index_path = my_index_path if not kth else kth_index_path
korpus_path = my_korpus_path if not kth else kth_korpus_path

# TBD
def construct():
    # Initialize an empty dictionary to store the unique three-letter prefixes and their positions
    three_letter_prefixes = {}
    
    print("Reading input file...")

    # Read the input file
    with open(index_path, 'r', encoding="latin-1") as infile:
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
    with open('labb1/three_letter.txt', 'w', encoding="latin-1") as outfile:
        for prefix, position in three_letter_prefixes.items():
            outfile.write(f"{prefix} {position}\n")

    print("Output file has been created.")