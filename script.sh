
echo "Now adding ip"
ip addr show lo |grep -i inet|sed -n '1p'|awk '{print $2}' >> temp

echo "Now calculating free memory"
free |grep Mem |awk '{print $4/$2 * 100.0}' >> temp

echo "Now calculating free hard disk space"
df |sed -n '2p'|awk '{print $4/$2 * 100.0}' >> temp
#df |sed -n '2p'|awk '{print $5}' >> output

sed '{:q;N;s/\n/,/g;t q}' temp >> output

#rm output temp

rm temp
