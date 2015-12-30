%% Author: Michal Sustr
% sustrmic@fel.cvut.cz

load data_33rpz_cv01.mat;

ims = permute(images,[1 2 4 3]); % The images must be ordered on the 4th dimension.
montage(ims); colormap gray;

%% 4) Display mean values of letters
% My initials are MS

Mpos=strfind(Alphabet, 'M');            % Get position of M
Spos=strfind(Alphabet, 'S');            % Get position of S
Mind=find(labels==Mpos);                % Get labels indices
Sind=find(labels==Spos);                
Mmean=uint8(mean(images(:,:,Mind),3));  % create mean and convert to uint8
Smean=uint8(mean(images(:,:,Sind),3));  

imshow(Mmean);                          % show image
figure;
imshow(Smean);                          

%% 5) Histogram of mean images

figure;
subplot(2,1,1);
hold on;

image=Mmean;
hist(double(image(find(image<128))), 128);

subplot(2,1,2);
image=Smean;
hist(double(image(find(image<128))), 128)
SUM(SUM(image<128))
