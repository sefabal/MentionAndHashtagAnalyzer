# The Identification of the Top-*N* Most Frequent @mentions and #hashtags in the 20 million Turkish Tweets

In this homework, we are going to indentify the top-*N* most frequent @mention and #hashtag entities. 
The dataset contains 20 million Turkish Tweets and can be downloded from [here](http://www.kemik.yildiz.edu.tr/data/File/20milyontweet.rar).

Option | Description
------------ | -------------
-n, --number | The number of entities to display. [defaults to 10]
-e, --entity | The name of the entity (e.g., hashtag, mention or emoji). [defaults to hashtag]
-r, --reverse | Reverse the comparison (e.g., display most infrequent entities).
-i, --ignore-case |	Fold upper case to lower case characters (e.g., collate #AnadoluÜniversitesi and #anadoluÜniversitesi).

The result will be printed to the standard output in the format of two columns (entity \t frequency) separated by a tab.

For example, `java jar target/trending.jar -n 20 -e mention -i Tweets.txt` will display top-20 mentions in decreasing order by their frequency.

Another example, `java jar target/trending.jar -r Tweets.txt` will display 10 hashtags in increasing order by their frequency.

