kth = True

raw_index_path = "labb1/rawindex.txt"
my_index_path = "labb1/index.txt"
my_korpus_path = "labb1/korpus"

three_letter_path = "labb1/three_letter.txt"

kth_default_path = "/afs/kth.se/misc/info/kurser/DD2350/adk23/labb1/"
kth_index_path = kth_default_path + "rawindex.txt"
kth_korpus_path = kth_default_path + "korpus"

index_path = raw_index_path if not kth else kth_index_path
korpus_path = my_korpus_path if not kth else kth_korpus_path
    
def construct(three_letter_only):
    # Initialize an empty dictionary to store the unique three-letter prefixes and their positions
    three_letter_prefixes = {}
    index = {}
    
    print("Reading input file...")
    
    position_in_file = 0

    # Read the input file
    with open(index_path, 'r', encoding='latin-1') as infile:
        
        counter = 0
        while True:
            line = infile.readline()
            if not line:
                break
            
            word, position = line.strip().split()
            
            # Take the first 3 letters from each word
            prefix = word[:3]
            
            # If the prefix is not in the dictionary, add it along with its position
            if prefix not in three_letter_prefixes:
                three_letter_prefixes[prefix] = position_in_file
            
            position_in_file += len(line)
                
            if word not in index:
                index[word] = {
                    "positions": [position],
                    "total_occurrences": 1,
                }
            else:
                index[word]["total_occurrences"] += 1
                index[word]["positions"].append(position)

    # Write the output file
    with open(three_letter_path, 'w', encoding='latin-1') as outfile:
        for prefix, position in three_letter_prefixes.items():
            outfile.write(f"{prefix} {position}\n")
         
    if three_letter_only:
        return   
    with open(my_index_path, "w", encoding="latin-1") as outfile:
        for word, data in index.items():
            first = True
            for position in data["positions"]:
                # check if first iteration
                if first:
                    outfile.write(f"{word} {position} {data['total_occurrences']} \n")
                    first = False
                    
                else:
                    outfile.write(f"{word} {position}\n")
            
    print("Output file has been created.")
    
construct(False)