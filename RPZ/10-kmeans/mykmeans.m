%% Load data

load image_data.mat

left_half  = 1:size(images,2)/2; % left half indices
right_half = size(images,2)/2+1:size(images,2);
top_half  = 1:size(images,1)/2; % left half indices
bottom_half = size(images,1)/2+1:size(images,1);

x=sum(sum(images(:, left_half, :))) - sum(sum(images(:, right_half, :)));
x=permute(x, [3 2 1]); % rearrange to get a column vector
y=sum(sum(images(top_half, :, :))) - sum(sum(images(bottom_half, :, :)));
y=permute(y, [3 2 1]); % rearrange to get a column vector
X=[x'; y'];

%%
load data.mat;

%% 
c=figure; ppatterns(X); pause(0.5);

k = 3;
max_steps = 100; % maximal number of steps for kmeans algorithm

centers = X(:, randi(length(X), 3, 1));
lastClasses = zeros(1, length(X));
avg_distances = zeros(1, max_steps);

means=zeros(size(X,1),k);
covariances=zeros(size(X,1), size(X,1), k);
P=zeros(1,k);

log_likelihood = zeros(1,max_steps);

for j=1:max_steps % prevent infinite loops
    distances = permute(sqrt(sum((repmat(X, [1 1 k]) - repmat(permute(centers, [1 3 2]), [1 length(X) 1])).^2, 1)), [3 2 1]);
    [~,classes] = min(distances);
    
    if(sum(classes ~= lastClasses) == 0)
        break;
    end
    lastClasses = classes;
    
    for i=1:k
        avg_distances(j) = avg_distances(j) + sum(distances(i, classes == i).^2);
        
        centers(:,i) = mean(X(:, classes==i),2);
        covariances(:, :, i) = cov(X(:, classes==i)');
        P(i) = sum(classes==i)/length(X);
    end
    avg_distances(j) = avg_distances(j)/length(X);
    
    
    log_likelihood(j) = likelihood(X,centers,covariances,P);
    
    % show development of classes
    clf(c);
    ppatterns(X,classes);
    plot(centers(1,:), centers(2,:), '+m', 'MarkerSize', 16);
    pause(0.5);
end

figure, plot(1:j, avg_distances(1:j));
figure, plot(1:j, log_likelihood(1:j));
%%
figure;
for i=1:k
    subplot(1,3,i);
    montage(permute(images(:,:,classes==i), [1 2 4 3])); 
end

figure;
for i=1:k
    subplot(1,3,i);
    imshow(mean(images(:,:,classes==i),3), [0 255])
end