load data_33rpz_pca;
data=trn_data.images;

%% calculate face-space base vectors
m = 9;   % number of base vectors
face = 10; % selected face

k = size(data, 3);
X=double(reshape(data, size(data,1)*size(data,2), size(data,3)));
mean_face=mean(X,2);
X_mean=X-repmat(mean_face, 1, size(X,2));
T=X_mean'*X_mean;
%T=X'*X;

% find m eigenvectors
[V,D]=eig(T);
[D_sorted, I]=sort(diag(D), 1, 'descend');
Y = X_mean*V(:,I(1:m));

%% Display face
figure(1);
subplot(121);
imshow(reshape(uint8(X(:,face)), size(data,1), size(data,2)));
title('Puvodni tvar');

%% Display mean face
figure;
imshow(reshape(uint8(mean_face), size(data,1), size(data,2)));
title('Mean face');

%% Display eigenface
eigenface=1;
figure;
imshow(reshape(uint8(Y(:,eigenface)+mean_face), size(data,1), size(data,2)));
title(['Eigen face #' num2str(eigenface)]);

%% Display eigenfaces in montage
D=zeros(size(data,1), size(data,2), 1, 4);
for i=1:4
    D(:,:,1,i) = reshape(Y(:,i)+mean_face, size(data,1), size(data,2));
end
montage(uint8(D),  'Size', [2 2]);


%% Normalize eigenfaces
Y_norm=zeros(size(Y));
for(i=1:m)
   Y_norm(:,i)=Y(:,i)/norm(Y(:,i));
end

%% Cumulative sum of eigenvalues
figure;
plot(1:size(D_sorted), cumsum(D_sorted(1:end)/sum(D_sorted)))
title('Cumulative sum of eigenvalues');

%% Reconstruct images


w=X_mean'*Y_norm;

reconstructed=Y_norm*w(face,:)';
figure(1);
subplot(122);
imshow(reshape(uint8(reconstructed+mean_face), size(data,1), size(data,2)));
title('Zrekonstruovana tvar');

'Reconstruction error (per pixel)'
sum(sqrt((reconstructed-(X(:,face)+mean_face)).^2))/size(X,1)

%% Nearest neighbours
% Calculate all weights for all faces
test_data = tst_data.images;
X_test= double(reshape(test_data, size(test_data,1)*size(test_data,2), size(test_data,3)));
X_test=X_test-repmat(mean(X_test,2), 1, size(X_test,2));
w_test = X_test'*Y_norm;

distances=permute(sum((repmat(permute(w_test, [3 2 1]), [size(w,1), 1, 1])-repmat(w, [1,1,size(w_test,1)])).^2, 2), [3 1 2]);
[dist_min, I] = min(distances, [], 2);

'Classification error'
1-sum(strcmp(trn_data.names(I), tst_data.names(:)'))/size(w_test,1)
