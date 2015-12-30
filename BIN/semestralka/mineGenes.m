function nm=mineGenes(eigengene,geneNames)

% eigengenes ... 1xM
% gene names ... cell array 1xM

[idx] = kmeans(eigengene(:),2,'replicates',50);

s1=sum(idx==1);
s2=sum(idx==2);
if s1<s2
    ig=find(idx==1);
else
    ig=find(idx==2);
end
nm=geneNames(ig);
nm=nm(strcmp(nm,'x')==0);


