load data_33rpz_cv08


%% Calculate measurements x and y
left_half  = 1:size(images,2)/2; % left half indices
right_half = size(images,2)/2+1:size(images,2);
x=sum(sum(images(:, left_half, :))) - sum(sum(images(:, right_half, :)));
x=permute(x, [3 2 1]); % rearrange to get a column vector
x=(x-min(x)) ./ (max(x)-min(x));

upper_half  = 1:size(images,1)/2; % upper half indices
lower_half = size(images,1)/2+1:size(images,1);
y=sum(sum(images(upper_half, :, :))) - sum(sum(images(lower_half, :, :)));
y=permute(y, [3 2 1]); % rearrange to get a column vector
y=(y-min(y)) ./ (max(y)-min(y));

X=[x'; y'];
y=labels;
y(y==2)=-1;

data.X = X; data.y = y;
model = show_svm(data, 10);

% Classify & compute error
classes=classify_svm(data,model);
error = sum(classes.new ~= classes.old)/length(classes.new)
